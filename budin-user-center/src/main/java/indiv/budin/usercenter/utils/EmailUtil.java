package indiv.budin.usercenter.utils;

import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
@ConfigurationProperties(prefix = "config.email")
public class EmailUtil {
    private static String host;
    private static String serverAddress;
    private static String authPass;

    private static Session session;
    private static Properties props = System.getProperties();

    static  {
        // 初始化props
        props.put("mail.smtp.auth", "true");
        if (host == null) props.put("mail.smtp.host", host);
        // 创建session
        session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(serverAddress, authPass);
            }
        });
        session.setDebug(true);
    }


    public static boolean send(String recipient, String subject, Object content,String type) {
        // 创建mime类型邮件
        MimeMessage message = new MimeMessage(session);
        try {
            // 设置发信人
            message.setFrom(new InternetAddress(serverAddress));
            // 设置收件人
            assert recipient != null;
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            // 设置主题
            message.setSubject(subject);
            // 设置邮件内容
            message.setContent(content.toString(), type);
            // 发送
            Transport.send(message);
            return true;
        } catch (MessagingException addressException){
            return false;
        }

    }


    public void setHost(String host) {
        EmailUtil.host = host;
    }

    public void setServerAddress(String serverAddress) {
        EmailUtil.serverAddress = serverAddress;
    }

    public void setAuthPass(String authPass) {
        EmailUtil.authPass = authPass;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setProps(Properties props) {
        EmailUtil.props = props;
    }
}
