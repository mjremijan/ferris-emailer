package org.ferris.emailer.main;

import org.ferris.emailer.application.ApplicationDirectory;
import org.ferris.emailer.email.EmailServer;
import org.ferris.emailer.settings.SettingsPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Michael
 */
public class Main {

    public static void main(String[] args)  {
        new Main().run();
    }
    
    private void run() {
        System.out.printf("Welcome to Ferris Emailer%n...see logs/application.log");
        setLogger();
        setApplicationDirectory();
        setSettingsPath();
        setEmailServer();      
    }
    
    private SettingsPath settingsPath;
    private void setSettingsPath() {
        settingsPath = new SettingsPath(getApplicationDirectory().toString());
    }
    private SettingsPath getSettingsPath() {
        return settingsPath;
    }
    
    private EmailServer emailServer;
    private EmailServer getEmailServer() {
        return emailServer;
    }
    private void setEmailServer() {
        emailServer = new EmailServer(
              LoggerFactory.getLogger(EmailServer.class)
            , getSettingsPath().toString()
        );
        log.info("Created EmailServer");
    }
    
    
    private ApplicationDirectory applicationDirectory;
    private ApplicationDirectory getApplicationDirectory() {
        return applicationDirectory;
    }
    private void setApplicationDirectory() {
        // This method assumes the application is executing
        // within a runtime created by jlink. If this is
        // the case, then the 'java.home' property is the 
        // root directory of directory structure created by jlink.
        applicationDirectory = new ApplicationDirectory(
            System.getProperty("java.home")
        );
        log.info("Created ApplicationDirectory");
    }
    
    private Logger log;
    private void setLogger() {
        log = LoggerFactory.getLogger(Main.class);
        log.info("Created Main Logger");
    }
}
