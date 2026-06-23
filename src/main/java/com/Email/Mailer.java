package com.Email;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Mailer {

    public static void sendMail() {

        String to = "abc@mail.com";
        String subject = "nill";
        String msg = "test";

        final String user = "YOUR_EMAIL";
        final String pass = "YOUR_APP_PASSWORD";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(msg);

            Transport.send(message);

            System.out.println("Mail Sent Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendMail();
    }
}