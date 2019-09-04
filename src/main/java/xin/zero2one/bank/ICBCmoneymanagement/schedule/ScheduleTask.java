package xin.zero2one.bank.ICBCmoneymanagement.schedule;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xin.zero2one.bank.ICBCmoneymanagement.model.MetalModel;
import xin.zero2one.bank.ICBCmoneymanagement.service.EmailService;
import xin.zero2one.bank.ICBCmoneymanagement.service.ICBCService;

import java.util.List;

/**
 * @author zhoujundong
 * @data 8/26/2019
 * @description TODO
 */
@Component
public class ScheduleTask {

    private static final String SEND_REALTIME_INFO_TOPIC = "贵金属实时信息";

    @Autowired
    private ICBCService icbcService;

    @Autowired
    private EmailService emailService;


    @Scheduled(cron = "0 0/10 * * * ?")
    public void sendInfo() {
        Gson gson = new Gson();
        List<MetalModel> realTimeMetalModel = icbcService.getRealTimeMetalModel();
        emailService.sendMsg(SEND_REALTIME_INFO_TOPIC, gson.toJson(realTimeMetalModel));
    }

}
