package org.ferris.emailer.mail;

import java.util.StringJoiner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class Message {

    protected String subject;
    protected String body;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[EmailDraft ", "]");
        sj.add(String.format("subject:%s", (subject == null) ? "null" : subject));
        sj.add(String.format("body_length:%s", (body == null) ? "null" : body.length()));
        if ("t".equals(System.getProperty("resiste_log_body", "f"))) {
            sj.add(String.format("body_contents:%s", (body == null) ? "null" : body));
        }
        return sj.toString();
    }

    public Message(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
