package com.test.mcp.email_mcp.tool;

import com.test.mcp.email_mcp.object.EmailRequest;
import com.test.mcp.email_mcp.service.GmailService;
import com.test.mcp.email_mcp.util.GmailUtils;
import jakarta.mail.internet.MimeMessage;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import com.google.api.services.gmail.model.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class GmailMcpTool {

    private final GmailService gmailService;

    public GmailMcpTool(GmailService gmailService) {
        this.gmailService = gmailService;
    }

    @Tool(name = "sendEmail", description = "Compose and dispatch an email message through the Gmail API")
    public String sendEmail(String from, String to, String subject, String body) throws Exception {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setBody(body);
        emailRequest.setFrom(from);
        emailRequest.setTo(to);
        emailRequest.setSubject(subject);
        MimeMessage message = GmailUtils.createMimeMessage(emailRequest);
        gmailService.sendEmail(message);
        return "Email sent successfully.";
    }

    @Tool(name = "trashEmail", description = "Mark the email for deletion by moving it to the trash folder")
    public String trashEmail(String messageId) throws IOException {
        gmailService.trashEmail(messageId);
        return "Email trashed.";
    }

    @Tool(name = "markEmailRead", description = "Mark the email as read")
    public String markEmailRead(String messageId) throws IOException {
        gmailService.markEmailRead(messageId);
        return "Email marked as read.";
    }

    @Tool(name = "markEmailUnread", description = "Mark the email as unread")
    public String markEmailUnread(String messageId) throws IOException {
        gmailService.markEmailUnread(messageId);
        return "Email marked as unread.";
    }

    @Tool(name = "readEmail", description = "Get metadata of the email")
    public String readEmail(String messageId) throws IOException {
        Message msg = gmailService.readEmail(messageId);
        return msg.toPrettyString();
    }

    @Tool(name = "openEmail", description = "Get full content of the email")
    public String openEmail(String messageId) throws IOException {
        Message msg = gmailService.openEmail(messageId);
        return msg.toPrettyString();
    }



    @Tool(name = "unReadEmail", description = "Get my un read email")
    public String unreadEmail() throws IOException {
        Object msg = gmailService.getUnreadEmails();
        return new ObjectMapper().writeValueAsString(msg);
    }

}
