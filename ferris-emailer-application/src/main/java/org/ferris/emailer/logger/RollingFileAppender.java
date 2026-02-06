package org.ferris.emailer.logger;

/**
 * Extends {@link RollingFileAppender} to programmatically
 * find the application's "logs" directory.
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RollingFileAppender extends ch.qos.logback.core.rolling.RollingFileAppender
{
    public RollingFileAppender() {
        super();
        super.setFile(
            new LogsPath().resolve("application.log").toString()
        );
    }
}
