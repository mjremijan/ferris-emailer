package org.ferris.emailer.main;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Michael
 */
public class Main {

    public static void main(String[] args)  {
        new Main();
    }
    
    private Main() {
        System.out.printf("Welcome to Ferris Emailer%n");
        //Path javaHome = Path.of(System.getProperty("java.home")).toAbsolutePath();
        //System.out.println("java.home = " + javaHome);
        
        LoggerContext context =
            (LoggerContext) LoggerFactory.getILoggerFactory();

        context.reset(); // clear any existing config

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%d{HH:mm:ss} %-5level %logger{36} - %msg%n");
        encoder.start();

        ConsoleAppender<ILoggingEvent> appender =
            new ConsoleAppender<>();
        appender.setContext(context);
        appender.setEncoder(encoder);
        appender.setImmediateFlush(true);
        appender.start();

        Logger root = context.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.DEBUG);
        root.addAppender(appender);
        
        org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());
        
        for (int i=0; i<100; i++) {
            log.info("INFO MESSAGE " + i);
        }        
    }
    
    
}
