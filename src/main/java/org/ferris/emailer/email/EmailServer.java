package org.ferris.emailer.email;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import org.slf4j.Logger;


/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailServer {
        
    protected Logger log;
    protected Properties props;
    protected Authenticator authenticator;
    protected InternetAddress from;
    
    public EmailServer(Logger log, String settingsPath) {
        setLog(log);
        setProperties(settingsPath);
        setAuthenticator();
        setFromAddress();
    }
    
    private void setLog(Logger log) {
        this.log = log;
    }
    
    private void setProperties(String settingsPath) {
        
        Path settingsFile = Path.of(settingsPath, "email.properties");
        if (!Files.exists(settingsFile)) {
            throw new RuntimeException(
                String.format("Settings file does not exist: \"%s\"", settingsFile.toString())
            );
        }
        if (!Files.isRegularFile(settingsFile)) {
            throw new RuntimeException(
                String.format("Settings file path is not to a file: \"%s\"", settingsFile.toString())
            );
        }
        
        this.props = new Properties();
        {
            try (InputStream is = new FileInputStream(settingsFile.toFile());) {
                props.load(is);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // auth
        props.setProperty("mail.smtp.auth", "true");
        
        // tls
        props.setProperty("mail.smtp.starttls.enable", "true");
        
        // host
        String host = props.getProperty("host", "").trim();
        {
            if (host.isEmpty()) {
                throw new RuntimeException("EmailServer property \"host\" is empty");
            }
            props.setProperty("mail.smtp.host", host);
        }
        
        // port
        String port = props.getProperty("port", "").trim();
        {
            if (port.isEmpty()) {
                throw new RuntimeException("EmailServer property \"port\" is empty");
            }
            try {
                Integer.parseInt(port);
            } catch (NumberFormatException e) {
                throw new RuntimeException("EmailServer property \"port\" is not an integer");
            }
            props.setProperty("mail.smtp.port", port);
        }
    }

    private void setAuthenticator() {
        authenticator = null;
        {
            String username = props.getProperty("username", "").trim();
            if (username.isEmpty()) {
                throw new RuntimeException("EmailServer property \"username\" is empty");
            }
            String password = props.getProperty("password", "").trim();
            if (password.isEmpty()) {
                throw new RuntimeException("EmailServer property \"password\" is empty");
            }
            authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            };
        }
    }
    
    private void setFromAddress() {
        from = new InternetAddress();
        {
            String fromAddress = props.getProperty("fromAddress", "").trim();
            if (fromAddress.isEmpty()) {
                throw new RuntimeException("EmailServer property \"fromAddress\" is empty");
            }
            String fromName = props.getProperty("fromName", "").trim();
            if (fromName.isEmpty()) {
                throw new RuntimeException("EmailServer property \"fromName\" is empty");
            }
            
            from.setAddress(fromAddress);
            try { from.setPersonal(fromName); } catch (UnsupportedEncodingException e) { throw new RuntimeException(e);}
        }
    }
    
    public void send(EmailMessage message)
    {
        log.info(String.format("ENTER %s", message));
        
        String subject = message.getSubject();
        String body = message.getBody();
        String toAddress = message.getToAddress();

        try {
            // create MimeMultipart
            MimeMultipart content = new MimeMultipart("related");

            // create html part
            {
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText(body, "UTF8", "html");
                content.addBodyPart(textPart);
            }

            Session smtp = null;
            {
                smtp = Session.getInstance(props, authenticator);
                smtp.setDebug(false);
            }

            MimeMessage m = new MimeMessage(smtp);
            {
                // to
                m.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
                
                // from
                m.setFrom(from);

                // reply
                m.setReplyTo(new InternetAddress[] {from});

                // subject
                m.setSubject(subject);

                // content
                m.setContent(content);
            }

            // send the email
            log.info(String.format("Attempt email to %s", toAddress));
            Transport.send(m);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
