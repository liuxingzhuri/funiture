package com.app.mvc.util;

import com.app.mvc.beans.Mail;
import com.app.mvc.configuration.DatabaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * Created by jimin on 15/11/21.
 */

@Slf4j
public class MailUtil {

    public static boolean send(Mail mail) {

        String from = DatabaseConfig.getStringValue("mail.send.from", "kanwangzjm@126.com");
        String host = DatabaseConfig.getStringValue("mail.send.smtp", "smtp.126.com");
        String pass = DatabaseConfig.getStringValue("mail.send.password", "880103");
        String nickname = DatabaseConfig.getStringValue("mail.send.nickname", "alert");

        HtmlEmail email = new HtmlEmail();
        try {
            email.setHostName(host);
            email.setCharset("UTF-8");
            for (String str : mail.getReceivers()) {
                email.addTo(str);
            }
            email.setFrom(from, nickname);
            email.setAuthentication(from, pass);
            email.setSubject(mail.getSubject());
            email.setMsg(mail.getMessage());
            email.send();
            log.info("{} 发送邮件到 {}", from, StringUtils.join(mail.getReceivers(), ","));
            return true;
        } catch (EmailException e) {
            log.error(from + "发送邮件到" + StringUtils.join(mail.getReceivers(), ",") + "失败", e);
            return false;
        }
    }

}

