package springboot.springboot.database.model;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {

    public void sendEmail(String name,String email,String passwordpatient) {
        final String username = "thuddth2307004@fpt.edu.vn";
        final String password = "kyxm zvbz nvsn uxxx";
        String subject = "Chúng đã đã tự động đăng kí tài khoản tại FPTHealth ";
        String body = "Xin chào "+name+" , để giúp bạn quản lí hồ sơ bệnh án , chúng tôi đã tạo tài khoản cho bạn trên hệ thồng , " +
                "bạn có thể đăng nhập bất cứ lúc nào để xem lại hồ sơ bệnh án , nhận những phần quà tri ân cực ấp dẫn và đóng góp những ý kiến phản hồi để chúng tôi nâng cao dịch vụ !"
                +"tài khoản của bạn là: " + email + " mật khẩu: " + passwordpatient
                ;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("thuddth2307004@fpt.edu.vn"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));

            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}