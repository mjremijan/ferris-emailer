package org.ferris.emailer.application;

import java.io.File;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class ApplicationDirectory extends File {

    private static final long serialVersionUID = 7491901906021288631L;

    public ApplicationDirectory(String path) {
        super(path);        
        if (!exists()) {
            throw new RuntimeException(
                String.format("ApplicationDirectory path does not exist: \"%s\"", getPath())
            );
        }
        if (!isDirectory()) {
            throw new RuntimeException(
                String.format("ApplicationDirectory path is not a directory: \"%s\"", getPath())
            );
        }
    }
}
