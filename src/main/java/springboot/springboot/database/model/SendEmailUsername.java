package springboot.springboot.database.model;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmailUsername {

    public void sendEmail(String name, String email, String passwordpatient) {
        final String username = "thuddth2307004@fpt.edu.vn";
        final String password = "kyxm zvbz nvsn uxxx";
        String subject = "Welcome to FPT Health";
        String body = "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; }" +
                ".container { max-width: 600px; margin: 20px auto; padding: 20px; background-color: #f0f0f0; border: 1px solid #ccc; border-radius: 8px; }" +
                ".header { background-color: #436FEC; color: white; padding: 10px; text-align: center; border-radius: 8px 8px 0 0; }" +
                ".content { padding: 20px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h2>Welcome to FPT Health</h2>" +
                "</div>" +
                "<div class='content'>" +
                "<p>Hi " + name + ",</p>" +
                "<p>To help you manage your medical records, we have created an account for you on the system.</p>" +
                "<p>Your account details:</p>" +
                "<ul>" +
                "<li><strong>Username:</strong> " + email + "</li>" +
                "<li><strong>Password:</strong> " + passwordpatient + "</li>" +
                "</ul>" +
                "<p>You can log in at any time to review your medical records.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";

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
            message.setContent(body, "text/html; charset=utf-8");

            Transport.send(message);

            System.out.println("Email sent successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}