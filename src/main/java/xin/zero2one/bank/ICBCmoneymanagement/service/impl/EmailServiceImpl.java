package xin.zero2one.bank.ICBCmoneymanagement.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import xin.zero2one.bank.ICBCmoneymanagement.service.EmailService;

import javax.mail.internet.MimeMessage;

/**
 * @author zhoujundong
 * @data 8/26/2019
 * @description TODO
 */
@Component
@Slf4j
public class EmailServiceImpl implements EmailService {


    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.toaddress}")
    private String toMailAddress;

    @Value("${spring.mail.username}")
    private String fromMailAddress;

    @Override
    public void sendMsg(String topic, String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            helper.setFrom(fromMailAddress);
            helper.setTo(toMailAddress);
            helper.setSubject(topic);
            helper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("send msg error", e);
        }
    }
}
