package com.test.mcp.email_mcp.util;
import com.test.mcp.email_mcp.object.EmailRequest;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.messaging.MessagingException;


import java.util.Properties;

public class GmailUtils {

    public static MimeMessage createMimeMessage(EmailRequest request) throws Exception {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(request.getFrom()));
        email.addRecipient(Message.RecipientType.TO, new InternetAddress(request.getTo()));
        email.setSubject(request.getSubject());
        email.setText(request.getBody());
        return email;
    }
}