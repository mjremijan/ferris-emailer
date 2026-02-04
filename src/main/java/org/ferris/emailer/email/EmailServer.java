package org.ferris.emailer.email;

import org.slf4j.Logger;


/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailServer {
        
    protected Logger log;
    
    public EmailServer(Logger log) {
        this.log = log;
    }

    public void send(EmailMessage message)
    {
        log.info(String.format("ENTER %s", message));

        try {
            // Create MimeMultipart
            MimeMultipart content = new MimeMultipart("related");
//
//            // html part
//            {
//                MimeBodyPart textPart = new MimeBodyPart();
//                textPart.setText(draft.getBody(), "UTF8", "html");
//                content.addBodyPart(textPart);
//            }
//
//            // properties            
//            Properties props = new Properties();
//            if (emailAccount.isSslEnabled()) {
//                props.setProperty("mail.smtp.auth", "true");
//                props.setProperty("mail.smtp.host", emailAccount.getHost());
//                props.setProperty("mail.smtp.socketFactory.port", emailAccount.getPort().toString());
//                props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//            } else {
//                props.setProperty("mail.smtp.auth", "true");
//                props.setProperty("mail.smtp.host", emailAccount.getHost());
//                props.setProperty("mail.smtp.port", emailAccount.getPort().toString());
//                props.setProperty("mail.smtp.starttls.enable", "true");
//            }
//
//            Session smtp = null;
//            {
//                smtp = Session.getInstance(props, new Authenticator() {
//                    @Override
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(
//                              emailAccount.getUsername()
//                            , emailAccount.getPassword()
//                        );
//                    }
//                });
//                smtp.setDebug(false);
//            }
//
//            MimeMessage m = new MimeMessage(smtp);
//            {
//                // to
//                m.setRecipient(
//                      Message.RecipientType.TO
//                    , new InternetAddress(emailAccount.getSendToAddress())
//                );
//
//                // subject
//                m.setSubject(draft.getSubject());
//
//                // from
//                {
//                    InternetAddress from = new InternetAddress(emailAccount.getEmailAddress());
//                    from.setPersonal("Resiste");
//                    m.setFrom(from);
//                }
//
//                // reply
//                {
//                    InternetAddress reply = new InternetAddress(emailAccount.getEmailAddress());
//                    reply.setPersonal("Resiste");
//                    m.setReplyTo(new InternetAddress[] {reply});
//                }
//
//                m.setContent(content);
//            }
//
//            log.info(String.format("Attempt email with %s", emailAccount.toString()));
//            Transport.send(m);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
