package com.sisw.alexpopa.folderhistorymonitor.service.impl;

import com.sisw.alexpopa.folderhistorymonitor.model.FileDetailsModel;
import com.sisw.alexpopa.folderhistorymonitor.model.FileModel;
import com.sisw.alexpopa.folderhistorymonitor.properties.DirectoryMonitorServiceProperties;
import com.sisw.alexpopa.folderhistorymonitor.resolver.FilePropertyDetailsResolver;
import com.sisw.alexpopa.folderhistorymonitor.service.DirectoryMonitorService;
import com.sisw.alexpopa.folderhistorymonitor.service.FileService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;

/**
 * @author Alex Daniel Popa
 */
@Service
@Log4j2
public class DirectoryMonitorServiceImpl implements DirectoryMonitorService {

    private WatchService watcher = null;
    private FilePropertyDetailsResolver filePropertyDetailsResolver = null;

    @Getter
    @Setter
    private DirectoryMonitorServiceProperties properties;

    @Autowired
    private FileService fileService;

    @Autowired
    public DirectoryMonitorServiceImpl(DirectoryMonitorServiceProperties properties) {
        this.properties = properties;

        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            log.error(e);
        }
    }

    @Override
    public void monitor() {
        if(watcher != null) {
            log.info("Monitoring path: " + properties.getDirectoryPath());

            Path path = Paths.get(properties.getDirectoryPath());

            try {
                path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);

                for (;;) {
                    WatchKey key;

                    try {
                        key = watcher.take();
                    } catch (InterruptedException e) {
                        return;
                    }

                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path filename = ev.context();

                        if (kind == StandardWatchEventKinds.OVERFLOW) {
                            log.error("OVERFLOW!!");
                        } else if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                            log.info("Created: " + filename);

                            Path filepath = Paths.get(path + "\\" + filename);
                            filePropertyDetailsResolver = new FilePropertyDetailsResolver();

                            try {
//                                log.info("File Name: " + filename);
//                                filePropertyDetailsResolver.resolveExtension(String.valueOf(filename)).ifPresent((value) -> log.info("File Extension: " + value));
//                                filePropertyDetailsResolver.resolveSize(filepath).ifPresent((value) -> log.info("File Size: " + value));
//                                filePropertyDetailsResolver.resolveCreationTime(filepath).ifPresent((value) -> log.info("File Creation Date: " + value));
//                                filePropertyDetailsResolver.resolveModificationTime(filepath).ifPresent((value) -> log.info("File Modification Date: " + value));

                                FileDetailsModel fileDetails = new FileDetailsModel();

                                filePropertyDetailsResolver.resolveExtension(String.valueOf(filename)).ifPresent(fileDetails::setExtension);
                                filePropertyDetailsResolver.resolveSize(filepath).ifPresent(fileDetails::setSize);
                                filePropertyDetailsResolver.resolveCreationTime(filepath).ifPresent(fileDetails::setCreationDate);
                                filePropertyDetailsResolver.resolveModificationTime(filepath).ifPresent(fileDetails::setModificationDate);

                                FileModel fileEntry = new FileModel();
                                fileEntry.setFilename(String.valueOf(filename));
                                fileEntry.setEventKind("ENTRY_CREATE");
                                fileEntry.setOperationDateTme(Instant.now());
                                fileEntry.setFileDetails(fileDetails);

                                log.info("Entry created: "+ fileService.createFileEntry(fileEntry).toString());

                            } catch (RuntimeException e) {
                                log.error(e);
                            }
                        } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                            log.info("Deleted: " + filename);

                            FileModel fileEntry = new FileModel();
                            fileEntry.setFilename(String.valueOf(filename));
                            fileEntry.setEventKind("ENTRY_DELETE");
                            fileEntry.setOperationDateTme(Instant.now());

                            log.info("Entry created: "+ fileService.createFileEntryNoDetails(fileEntry).toString());
                        }

                        boolean valid = key.reset();
                        if (!valid) {
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                log.error(e);
            }
        }
    }
}
