package xin.zero2one.bank.ICBCmoneymanagement.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xin.zero2one.bank.ICBCmoneymanagement.message.MessageUtils;
import xin.zero2one.bank.ICBCmoneymanagement.model.MetalModel;
import xin.zero2one.bank.ICBCmoneymanagement.model.NobleMetalsModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoujundong on 2018/10/8.
 */
public class NobleMetalsApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(NobleMetalsApi.class);

    private static double gold = 264.00;
    private static double silver = 3.20;

    public static void getNobleMetalsData(String phoneNo) throws IOException {
        String url = "https://mybank.icbc.com.cn/servlet/AsynGetDataServlet";
        HttpResponse response = Request.Post(url)
                .setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .bodyString("Area_code=0200&trademode=1&proIdsIn=&isFirstTime=1&tranCode=A00500", ContentType.create("application/x-www-form-urlencoded"))
                .execute()
                .returnResponse();

        InputStream content = response.getEntity().getContent();

        String string = IOUtils.toString(content, "UTF-8");

        Gson gson = new Gson();
        NobleMetalsModel model = gson.fromJson(string, NobleMetalsModel.class);

        List<MetalModel> market = model.getMarket();

        for(MetalModel metalModel : market){
            if (metalModel.getMetalname().equalsIgnoreCase("人民币账户黄金")){
                String buyprice = metalModel.getBuyprice();
                String sellprice = metalModel.getSellprice();
                double v = Double.parseDouble(sellprice);
                LOGGER.info("黄金售出点数:" + sellprice);
                LOGGER.info("黄金购买点数:" + buyprice);

                if (v <= gold){
                    String message = sellprice.replace(".", "");
                    MessageUtils.sendMessage(phoneNo, message);
                    break;
                }
            }

            if (metalModel.getMetalname().equalsIgnoreCase("人民币账户白银")){
                String buyprice = metalModel.getBuyprice();
                String sellprice = metalModel.getSellprice();
                double v = Double.parseDouble(sellprice);
                LOGGER.info("白银售出点数:" + sellprice);
                LOGGER.info("白银购买点数:" + buyprice);
                if (v <= silver){
                    MessageUtils.sendMessage(phoneNo, /*"白银售出点数:" + */sellprice);
                    break;
                }

            }
        }
    }




}
