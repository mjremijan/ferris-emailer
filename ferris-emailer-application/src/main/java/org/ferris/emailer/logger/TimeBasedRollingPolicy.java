package org.ferris.emailer.logger;

import java.io.File;
import java.net.URISyntaxException;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class TimeBasedRollingPolicy extends ch.qos.logback.core.rolling.TimeBasedRollingPolicy
{
    public TimeBasedRollingPolicy() throws URISyntaxException {
        super();
        super.setFileNamePattern(
            new LogsPath() + File.separator + "application.%d{yyyy-MM}.log"
        );
        // https://stackoverflow.com/questions/10953915/filenamepattern-in-rollingfileappender-logback-configuration
    }
}
