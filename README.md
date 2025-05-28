#email-mcp (Model Context Protocol Email Server)
This project implements a Model Context Protocol (MCP) server in Java, enabling seamless integration of email functionalities—such as sending, reading, and managing emails—into Large Language Model (LLM) environments like Claude Desktop.
GitHub

🚀 Features
Send Emails: Compose and dispatch emails through the MCP interface.

Read Emails: Retrieve and display emails from your inbox.

Manage Emails: Perform operations like deleting or archiving emails.

LLM Integration: Facilitates natural language interactions with email services via LLMs.
GitHub

🛠️ Getting Started
Prerequisites
Java Development Kit (JDK) 11 or higher

Maven 3.6 or higher

An email account (e.g., Gmail) with SMTP and IMAP access enabled

Installation
Clone the Repository

bash
Copy
Edit
git clone https://github.com/AravindSundarraj/email-mcp.git
cd email-mcp
Build the Project

bash
Copy
Edit
mvn clean install
Configure Email Settings

Create a config.properties file in the src/main/resources directory with the following content:

properties
Copy
Edit
email.username=your_email@example.com
email.password=your_email_password
email.smtp.host=smtp.example.com
email.smtp.port=587
email.imap.host=imap.example.com
email.imap.port=993
Note: For Gmail users, ensure that IMAP is enabled and consider using an App Password for enhanced security.

Run the MCP Server

bash
Copy
Edit
java -jar target/email-mcp-1.0-SNAPSHOT.jar
🤖 Integrating with Claude Desktop
To enable Claude Desktop to interact with your MCP server:

Locate the Configuration File

Windows: %APPDATA%\Claude\claude_desktop_config.json

macOS/Linux: ~/Library/Application Support/Claude/claude_desktop_config.json
GitHub
+1
GitHub
+1

Add MCP Server Configuration

json
Copy
Edit
{
  "mcpServers": {
    "email-mcp": {
      "command": "java",
      "args": [
        "-jar",
        "path/to/email-mcp-1.0-SNAPSHOT.jar"
      ]
    }
  }
}
Replace path/to/email-mcp-1.0-SNAPSHOT.jar with the actual path to your JAR file.

Restart Claude Desktop

After restarting, Claude Desktop should recognize and be able to utilize the email MCP server.

🧪 Example Prompts
"Send an email to John with the subject 'Meeting Reminder' and body 'Don't forget our meeting at 10 AM tomorrow.'"

"Read my latest unread email."

"Delete the email from 'newsletter@example.com'."
