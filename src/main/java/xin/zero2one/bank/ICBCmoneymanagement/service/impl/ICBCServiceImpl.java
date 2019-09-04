package xin.zero2one.bank.ICBCmoneymanagement.service.impl;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import xin.zero2one.bank.ICBCmoneymanagement.constants.MetalMsgConstants;
import xin.zero2one.bank.ICBCmoneymanagement.dao.IICBCDao;
import xin.zero2one.bank.ICBCmoneymanagement.model.MetalModel;
import xin.zero2one.bank.ICBCmoneymanagement.model.MetalType;
import xin.zero2one.bank.ICBCmoneymanagement.model.NobleMetalsModel;
import xin.zero2one.bank.ICBCmoneymanagement.service.EmailService;
import xin.zero2one.bank.ICBCmoneymanagement.service.ICBCService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhoujundong on 2018/10/31.
 */
@Service
@Slf4j
public class ICBCServiceImpl implements ICBCService {

    @Autowired
    private IICBCDao icbcDao;

    @Autowired
    private EmailService emailService;


    private final ThreadPoolExecutor POOL;
    {
        POOL = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }

    private Gson GSON = new Gson();

    private Map<MetalType,Double> ceil = new ConcurrentHashMap<>();

    private Map<MetalType,Double> floor = new ConcurrentHashMap<>();

    @Override
    public List<MetalModel> findAllMetalModels() {
        return icbcDao.findAll();
    }

    @Override
    public List<MetalModel> findMetalModelsByPage(int page, int size){
        Sort sort = new Sort(Sort.Direction.DESC,"time");
        Pageable pageable = new PageRequest(page , size, sort);
        Page<MetalModel> all = icbcDao.findAll(pageable);
        return all.getContent();
    }

    @Override
    public List<MetalModel> findMetalModel(int page, int size, int type){
        Sort sort = new Sort(Sort.Direction.DESC,"time");
        Pageable pageable = new PageRequest(page , size, sort);
        Page<MetalModel> all = icbcDao.findAll(new Specification<MetalModel>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<MetalModel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("type").as(int.class), type);
            }
        }, pageable);
        return all.getContent();
    }


    @Override
    public MetalModel saveMetalModel(MetalModel metalModel){
        return icbcDao.save(metalModel);
    }

    @Override
    public List<MetalModel> getRealTimeMetalModel() {
        String url = "https://mybank.icbc.com.cn/servlet/AsynGetDataServlet";
        String result = "";
        try {
            HttpResponse response = Request.Post(url)
                    .setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .bodyString("Area_code=0200&trademode=1&proIdsIn=&isFirstTime=1&tranCode=A00462", ContentType.create("application/x-www-form-urlencoded"))
                    .execute()
                    .returnResponse();

            InputStream content = response.getEntity().getContent();

            result = IOUtils.toString(content, "UTF-8");
        } catch (IOException e){
            log.error("get and save metal metal error", e);
        }

        List<MetalModel> metalModels = Lists.newArrayListWithCapacity(8);
        NobleMetalsModel model = GSON.fromJson(result, NobleMetalsModel.class);
        List<MetalModel> market = model.getMarket();
        market.stream().forEach(metalModel -> {
            metalModel.setTime(System.currentTimeMillis());
            if (MetalType.RMB_GOLD.getMetalName().equalsIgnoreCase(metalModel.getMetalname())){
                metalModel.setType(0);
            }
            if (MetalType.RMB_SILVER.getMetalName().equalsIgnoreCase(metalModel.getMetalname())){
                metalModel.setType(1);
            }
            if (MetalType.RMB_PLATINUM.getMetalName().equalsIgnoreCase(metalModel.getMetalname())){
                metalModel.setType(2);
            }
            if (MetalType.RMB_PALLADIUM.getMetalName().equalsIgnoreCase(metalModel.getMetalname())){
                metalModel.setType(3);
            }
            if (MetalType.DOLLAR_GOLD.getMetalName().equalsIgnoreCase(metalModel.getMetalname())){
                metalModel.setType(4);
            }
            if (MetalType.DOLLAR_SILVER.getMetalName().equalsIgnoreCase(metalModel.getMetalname())){
                metalModel.setType(5);
            }
            if (MetalType.DOLLAR_PLATINUM.getMetalName().equalsIgnoreCase(metalModel.getMetalname())){
                metalModel.setType(6);
            }
            if (MetalType.DOLLAR_PALLADIUM.getMetalName().equalsIgnoreCase(metalModel.getMetalname())){
                metalModel.setType(7);
            }
            metalModels.add(metalModel);
        });
        return metalModels;
    }

    @Override
    public List<MetalModel> getAndSaveMetalModel() {
        List<MetalModel> realTimeMetalModel = getRealTimeMetalModel();
        realTimeMetalModel.stream().forEach(metalModel -> this.saveMetalModel(metalModel));
        return realTimeMetalModel;
    }

    @Override
    public void setCeil(double ceilPrice, String metalType) {
        ceil.put(MetalType.getMetalType(metalType), ceilPrice);
    }

    @Override
    public void setFloor(double floorPrice, String metalType){
        floor.put(MetalType.getMetalType(metalType), floorPrice);
    }

    @Override
    public void sendMsg() {
        ceil.forEach((metalType,value) -> {
            MetalModel metalModel = findMetalModelByType(metalType);
            String buyprice = metalModel.getBuyprice();
            if (Double.parseDouble(buyprice) > value){
                String topic = String.format(MetalMsgConstants.CEIL_MSG,
                        metalType.getMetalName(), buyprice, value);
                emailService.sendMsg(topic, GSON.toJson(metalModel));
            }
        }) ;

        floor.forEach((metalType,value) -> {
            MetalModel metalModel = findMetalModelByType(metalType);
            String buyprice = metalModel.getBuyprice();
            if (Double.parseDouble(buyprice) < value){
                String topic = String.format(MetalMsgConstants.FLOOR_MSG,
                        metalType.getMetalName(), buyprice, value);
                emailService.sendMsg(topic, GSON.toJson(metalModel));
            }
        }) ;
    }

    @Override
    public Map<MetalType,Double> findCeilSettings() {
        return ceil;
    }

    @Override
    public Map<MetalType,Double> findFloorSettings() {
        return floor;
    }

    private MetalModel findMetalModelByType(MetalType metalType){
        log.debug("metalTypeis {}", metalType);
        List<MetalModel> metalModelList = this.findMetalModel(1, 1, metalType.getMetalValue());
        MetalModel metalModel = metalModelList.get(0);
        log.debug("metal model info is {}", GSON.toJson(metalModel));
        return metalModel;
    }

}
