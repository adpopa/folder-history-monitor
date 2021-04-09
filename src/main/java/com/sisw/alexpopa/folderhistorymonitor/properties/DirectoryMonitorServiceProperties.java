package com.sisw.alexpopa.folderhistorymonitor.properties;

import com.sun.istack.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author Alex Daniel Popa
 */
@Component
@ConfigurationProperties(prefix = "directory-monitor-service")
public class DirectoryMonitorServiceProperties {

    private String directoryPath;

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {

        if(directoryPath == null)
            throw new NullPointerException("Value cannot be null");

        this.directoryPath = directoryPath;
    }
}
