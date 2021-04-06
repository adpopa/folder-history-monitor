package com.sisw.alexpopa.folderhistorymonitor;

import com.sisw.alexpopa.folderhistorymonitor.service.DirectoryMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableConfigurationProperties
public class FolderHistoryMonitorApplication {

	@Autowired
	private DirectoryMonitorService superviser;

	public static void main(String[] args) {
		SpringApplication.run(FolderHistoryMonitorApplication.class, args);
	}

	@PostConstruct
	public void enableDirectoryMonitor() {
		superviser.monitor();
	}
}
