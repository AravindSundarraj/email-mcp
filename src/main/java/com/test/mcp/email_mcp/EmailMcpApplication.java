package com.test.mcp.email_mcp;

import com.test.mcp.email_mcp.config.GmailConfig;
import com.test.mcp.email_mcp.tool.GmailMcpTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmailMcpApplication {

	private static final Logger logger = LoggerFactory.getLogger(EmailMcpApplication.class);
	public static void main(String[] args) {

		Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
			//logger.error("Unhandled exception: " , e.getMessage());
			//System.err.println("Unhandled exception: " + e.getMessage());
			//e.printStackTrace();
		});


		SpringApplication.run(EmailMcpApplication.class, args);
	}


	@Bean
	public ToolCallbackProvider emailTools(GmailMcpTool gmailMcpTool) {
		return MethodToolCallbackProvider.builder().toolObjects(gmailMcpTool).build();
	}

}
