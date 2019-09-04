package xin.zero2one.bank.ICBCmoneymanagement.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import xin.zero2one.bank.ICBCmoneymanagement.service.ICBCService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhoujundong on 2018/10/8.
 */
//@Component
public class Init implements ApplicationRunner {

    ScheduledExecutorService delayUpdateService = Executors.newScheduledThreadPool(5);

    @Autowired
    private ICBCService icbcService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        startMsgTask();
        startPullMetalInfoTask();

    }

    private void startMsgTask(){
        delayUpdateService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
//                    NobleMetalsApi.getNobleMetalsData("17611583306");
                    icbcService.sendMsg();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    private void startPullMetalInfoTask(){
        delayUpdateService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
//                icbcService.saveMetalModel()
                icbcService.getAndSaveMetalModel();
            }
        }, 0, 30, TimeUnit.SECONDS);
    }
}
