//package com.test.mcp.email_mcp.controller;
//
//
//import com.google.api.services.gmail.model.Message;
//import com.test.mcp.email_mcp.object.EmailRequest;
//import com.test.mcp.email_mcp.service.GmailService;
//import com.test.mcp.email_mcp.util.GmailUtils;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/gmail")
//public class GmailController {
//
//    private final GmailService gmailService;
//
//
//    @Autowired
//    public GmailController(GmailService gmailService) {
//        this.gmailService = gmailService;
//
//    }
//
//    @PostMapping("/send")
//    public ResponseEntity<String> sendEmail(@RequestParam String to,
//                                            @RequestParam String subject,
//                                            @RequestParam String body) {
//        try {
//            EmailRequest emailRequest = new EmailRequest();
//            emailRequest.setBody(body);
//           emailRequest.setFrom("aravind.ssr@gmail.com");
//            emailRequest.setTo(to);
//            emailRequest.setSubject(subject);
//            MimeMessage message = GmailUtils.createMimeMessage(emailRequest);
//            GmailUtils.createMimeMessage(emailRequest);
//
//
//            gmailService.sendEmail(message);
//            return ResponseEntity.ok("Email sent successfully");
//
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body("Failed to send email: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/trash")
//    public ResponseEntity<String> trashEmail(@RequestParam String messageId) {
//        try {
//            gmailService.trashEmail(messageId);
//            return ResponseEntity.ok("Email trashed successfully");
//        } catch (IOException e) {
//            return ResponseEntity.internalServerError().body("Failed to trash email: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/read")
//    public ResponseEntity<String> markRead(@RequestParam String messageId) {
//        try {
//            gmailService.markEmailRead(messageId);
//            return ResponseEntity.ok("Email marked as read");
//        } catch (IOException e) {
//            return ResponseEntity.internalServerError().body("Failed to mark email as read: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/unread")
//    public ResponseEntity<String> markUnread(@RequestParam String messageId) {
//        try {
//            gmailService.markEmailUnread(messageId);
//            return ResponseEntity.ok("Email marked as unread");
//        } catch (IOException e) {
//            return ResponseEntity.internalServerError().body("Failed to mark email as unread: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/read")
//    public ResponseEntity<Object> readEmail(@RequestParam String messageId) {
//        try {
//            Message message = gmailService.readEmail(messageId);
//            return ResponseEntity.ok(message);
//        } catch (IOException e) {
//            return ResponseEntity.internalServerError().body("Failed to read email: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/open")
//    public ResponseEntity<Object> openEmail(@RequestParam String messageId) {
//        try {
//            Message message = gmailService.openEmail(messageId);
//            return ResponseEntity.ok(message);
//        } catch (IOException e) {
//            return ResponseEntity.internalServerError().body("Failed to open email: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/unread-emails")
//    public ResponseEntity<Object> getUnreadEmails() {
//        Object result = gmailService.getUnreadEmails();
//        return ResponseEntity.ok(result);
//    }
//}
//
