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
import java.io.UnsupportedEncodingException;
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
    
    public EmailServer(Logger log, String host, int port, String username, String password, String fromAddress, String fromName) {
        this.log = log;
        
        this.props = new Properties();
        {
            props.setProperty("mail.smtp.host", host);
            props.setProperty("mail.smtp.port", String.valueOf(port));
            props.setProperty("mail.smtp.auth", "true");                       
            props.setProperty("mail.smtp.starttls.enable", "true");
        }
        
        this.authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        
        from = new InternetAddress();
        {
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
