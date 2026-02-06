package org.ferris.emailer.logger;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class LogsPath {
    protected Path logsPath;
    
    public LogsPath() {
        // This method assumes the application is executing
        // within a runtime created by jlink. If this is
        // the case, then the 'java.home' property is the 
        // root directory of directory structure created by jlink.
        // The /logs directory should be subdirectory like this:
        //
        //  /root_created_by_jlink
        //    /logs
        //
        logsPath = Path.of(System.getProperty("java.home")).resolve("logs");
        if (!Files.exists(logsPath)) {
            throw new RuntimeException(
                String.format("Logs path does not exist: \"%s\"", logsPath.toString())
            );        
        }
        if (!Files.isDirectory(logsPath)) {
            throw new RuntimeException(
                String.format("Logs path is not a directory: \"%s\"", logsPath.toString())
            );
        }      
    }
  
    public Path resolve(String other) {
        return logsPath.resolve(other);
    }
    
    @Override
    public String toString() {
        return logsPath.toString();
    }
}
