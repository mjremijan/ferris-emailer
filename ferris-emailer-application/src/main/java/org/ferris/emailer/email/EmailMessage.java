package org.ferris.emailer.email;

import java.util.StringJoiner;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailMessage {

    protected String subject;
    protected String body;
    protected String toAddress;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[EmailMessage ", "]");
        sj.add(String.format("toAddress:%s", (toAddress == null) ? "null" : toAddress));
        sj.add(String.format("subject:%s", (subject == null) ? "null" : subject));
        sj.add(String.format("body_length:%s", (body == null) ? "null" : body.length()));
        return sj.toString();
    }

    public EmailMessage(String subject, String body, String toAddress) {
        this.subject = subject;
        this.body = body;
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getToAddress() {
        return this.toAddress;
    }
}
