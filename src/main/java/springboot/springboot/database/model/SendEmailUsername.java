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
                "body { font-family: Arial, sans-serif;margin: 0;padding: 0; }" +
                ".container { max-width: 600px; margin: 20px auto; padding: 20px; background-color: #f0f0f0; border: 1px solid #ccc; border-radius: 8px;box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
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
                "<p>Hi <strong>" + name + "</strong>,</p>" +
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

    public void sendEmailFormRegister(String doctorName, String departmentName, String appointmentDate, String patientEmail, String patientName) {
        final String username = "thuddth2307004@fpt.edu.vn";
        final String password = "kyxm zvbz nvsn uxxx";
        String subject = "Chúc mừng bạn đã đăng kí khám thành công tại FPT Health ";
        String body = "Xin chào " + patientName + " , bạn đã đặt khám thành công tại FPT Health , Khoa khám bệnh của bạn là: " + departmentName +
                "Bác sĩ khám cho bạn là: " + doctorName + "Ngày khám của bạn là:" + appointmentDate
                + " Nhân viên của chúng tôi sẽ liên hệ với bạn để huướng dẫn bạn chi tiết . Trân trọng !";

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
                    InternetAddress.parse(patientEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);

            System.out.println("Email sent successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendEmailForgot(String name, String email, String code) {
        final String username = "thuddth2307004@fpt.edu.vn";
        final String password = "kyxm zvbz nvsn uxxx";
        String subject = "Password Reset Request";
        String body = "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif;margin: 0;padding: 0; }" +
                ".container { max-width: 600px; margin: 20px auto; padding: 20px; background-color: #f0f0f0; border: 1px solid #ccc; border-radius: 8px;box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
                ".header { background-color: #436FEC; color: white; padding: 10px; text-align: center; border-radius: 8px 8px 0 0; }" +
                ".content { padding: 20px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h2>Password Reset Request</h2>" +
                "</div>" +
                "<div class='content'>" +
                "<p>Hi <strong>" + name + "</strong>,</p>" +
                "<p>We have received a request to retrieve your password. For security purposes, please do not share the code below with anyone.</p>" +
                "<p>Your verify code is:</p>" +
                "<h3> " + code + "</h3>" +
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
    public void sendEmailReply(String name, String email, String message) {
        final String username = "thuddth2307004@fpt.edu.vn";
        final String password = "kyxm zvbz nvsn uxxx";
        String subject = "Reply from Admin";

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
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}