package com.sisw.alexpopa.folderhistorymonitor.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Alex Daniel Popa
 */
@Component
@ConfigurationProperties(prefix = "directory-monitor-service")
public class DirectoryMonitorServiceProperties {

    @Getter
    @Setter
    private String directoryPath;

}
