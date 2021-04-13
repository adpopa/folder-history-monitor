package com.sisw.alexpopa.folderhistorymonitor.config;

import com.sisw.alexpopa.folderhistorymonitor.service.DirectoryMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @author Alex Daniel Popa
 */
//@Configuration
public class MyAppConfiguration {

    @Autowired
	private DirectoryMonitorService superviser;

    @EventListener(ApplicationReadyEvent.class)
    public void enableDirectoryMonitor() {
        superviser.monitor();
    }

}
