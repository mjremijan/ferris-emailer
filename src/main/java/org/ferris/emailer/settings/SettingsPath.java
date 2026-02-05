package org.ferris.emailer.settings;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class SettingsPath {

    protected Path path;

    public SettingsPath(String applicationDirectory) {
        path = Path.of(applicationDirectory, "settings");
        
        if (!Files.exists(path)) {
            throw new RuntimeException(
                String.format("Settings path does not exist: \"%s\"", path.toString())
            );
        }
        if (!Files.isDirectory(path)) {
            throw new RuntimeException(
                String.format("Settings path is not a directory: \"%s\"", path.toString())
            );
        }
    }
    
    @Override
    public String toString() {
        return path.toString();
    }
}
