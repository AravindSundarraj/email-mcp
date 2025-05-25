package com.test.mcp.email_mcp.config;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.*;
import java.util.List;

@Configuration
public class GmailConfig {

    private static final String APPLICATION_NAME = "My Gmail App";
    private static final Logger logger = LoggerFactory.getLogger(GmailConfig.class);
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = List.of(
            GmailScopes.GMAIL_MODIFY,
            GmailScopes.GMAIL_SEND,
            GmailScopes.GMAIL_READONLY
    );
    private static final String TOKENS_DIRECTORY_PATH =
            System.getProperty("user.home") + "/.gmail-tokens";


    @Bean
    @Lazy
    public Gmail gmail() throws Exception {
        logger.info("Loading credentials from classpath...");
        // Load client secrets
        InputStream in = getClass().getClassLoader().getResourceAsStream("/credentials.json");
        System.err.println("Loading credentials from classpath...2");
        if (in == null) {
            System.err.println("Resource file credentials.json not found in classpath");
          //  throw new FileNotFoundException("Resource file credentials.json not found in classpath");
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY,
                new InputStreamReader(in)
        );
        System.err.println("Loading credentials from classpath...3");
      //  System.err.println("Loading credentials.json from classpath... loaded");

        // Build flow and trigger user authorization request


        // Set up local server receiver

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        GoogleAuthorizationCodeFlow flow = null;
        logger.info("Before Authorizing.....");
        Credential credential = null;
        try {
             flow = new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY,
                    clientSecrets,
                    SCOPES
            )
                    .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                    .setAccessType("offline")
                    .build();
            credential  = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        }
        catch (Exception ex){
            logger.error("Mail Token Exception {} ", ex);
        }
        // Build and return Gmail client
        return new Gmail.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential
        ).setApplicationName(APPLICATION_NAME).build();
    }


}
