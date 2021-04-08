package com.sisw.alexpopa.folderhistorymonitor;

//import com.sisw.alexpopa.folderhistorymonitor.config.MyAppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@SpringBootApplication
@EnableConfigurationProperties
public class FolderHistoryMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FolderHistoryMonitorApplication.class, args);
	}
}
