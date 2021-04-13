package com.sisw.alexpopa.folderhistorymonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class FolderHistoryMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FolderHistoryMonitorApplication.class, args);
	}
}
