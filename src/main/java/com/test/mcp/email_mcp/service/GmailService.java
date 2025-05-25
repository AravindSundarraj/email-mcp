package com.test.mcp.email_mcp.service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.ModifyMessageRequest;
import com.test.mcp.email_mcp.config.GmailConfig;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class GmailService {


    private static final Logger logger = LoggerFactory.getLogger(GmailService.class);
    private final Gmail gmail;

    public GmailService(Gmail gmail) {
        this.gmail = gmail;
    }

    public void sendEmail(MimeMessage mimeMessage) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        mimeMessage.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.getUrlEncoder().encodeToString(bytes);

        Message message = new Message();
        message.setRaw(encodedEmail);

        gmail.users().messages().send("me", message).execute();
    }

    public void trashEmail(String messageId) throws IOException {
        gmail.users().messages().trash("me", messageId).execute();
    }

    public void markEmailRead(String messageId) throws IOException {
        modifyLabels(messageId, List.of("UNREAD"), List.of());
    }

    public void markEmailUnread(String messageId) throws IOException {
        modifyLabels(messageId, List.of(), List.of("UNREAD"));
    }

    public Message readEmail(String messageId) throws IOException {
        return gmail.users().messages().get("me", messageId).setFormat("metadata").execute();
    }

    public Message openEmail(String messageId) throws IOException {
        return gmail.users().messages().get("me", messageId).setFormat("full").execute();
    }



        public Object getUnreadEmails() {
            String userId = "me";
            String query = "in:inbox is:unread category:primary";
            List<Message> messages = new ArrayList<>();

            try {
                Gmail.Users.Messages.List request = gmail.users().messages().list(userId).setQ(query);
                ListMessagesResponse response = request.execute();

                if (response.getMessages() != null) {
                    messages.addAll(response.getMessages());
                }

                while (response.getNextPageToken() != null) {
                    String pageToken = response.getNextPageToken();
                    request.setPageToken(pageToken);
                    response = request.execute();
                    if (response.getMessages() != null) {
                        messages.addAll(response.getMessages());
                    }
                }

                return messages;

            } catch (GoogleJsonResponseException e) {
               // System.err.println("Gmail Exception : " +   e.getMessage());
                e.printStackTrace();
                return "A GoogleJsonResponseException occurred: " + e.getDetails();
            } catch (IOException e) {
                return "An IOException occurred: " + e.getMessage();
            }
        }



    private void modifyLabels(String messageId, List<String> remove, List<String> add) throws IOException {
        ModifyMessageRequest mods = new ModifyMessageRequest()
                .setRemoveLabelIds(remove)
                .setAddLabelIds(add);
        gmail.users().messages().modify("me", messageId, mods).execute();
    }
}