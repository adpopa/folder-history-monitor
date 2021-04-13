package com.sisw.alexpopa.folderhistorymonitor.service.impl;

import com.sisw.alexpopa.folderhistorymonitor.grpc.monitor.DirectoryMonitorServiceGrpc;
import com.sisw.alexpopa.folderhistorymonitor.grpc.monitor.MonitorRequest;
import com.sisw.alexpopa.folderhistorymonitor.grpc.monitor.MonitorResponse;
import com.sisw.alexpopa.folderhistorymonitor.model.FileDetailsModel;
import com.sisw.alexpopa.folderhistorymonitor.model.FileModel;
import com.sisw.alexpopa.folderhistorymonitor.properties.DirectoryMonitorServiceProperties;
import com.sisw.alexpopa.folderhistorymonitor.resolver.FilePropertyDetailsResolver;
import com.sisw.alexpopa.folderhistorymonitor.service.DirectoryMonitorService;
import com.sisw.alexpopa.folderhistorymonitor.service.FileService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;

/**
 * @author Alex Daniel Popa
 */
//@Service
@GrpcService
public class DirectoryMonitorServiceImpl extends DirectoryMonitorServiceGrpc.DirectoryMonitorServiceImplBase {

    private static final Logger LOGGER = LogManager.getLogger(DirectoryMonitorServiceImpl.class);

    private WatchService watcher = null;
    private FilePropertyDetailsResolver filePropertyDetailsResolver = null;

    private DirectoryMonitorServiceProperties properties;

    @Autowired
    private FileService fileService;

    @Autowired
    public DirectoryMonitorServiceImpl(DirectoryMonitorServiceProperties properties) {
        this.properties = properties;

        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void monitor(MonitorRequest request, StreamObserver<MonitorResponse> responseObserver) {
        if(watcher != null) {
            LOGGER.info("Monitoring path: " + properties.getDirectoryPath());

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
                            LOGGER.error("OVERFLOW!!");
                        } else if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                            LOGGER.info("Created: " + filename);

                            Path filepath = Paths.get(path + "\\" + filename);
                            filePropertyDetailsResolver = new FilePropertyDetailsResolver();

                            try {
//                                log.info("File Name: " + filename);
//                                filePropertyDetailsResolver.resolveExtension(String.valueOf(filename)).ifPresent((value) -> LOGGER.info("File Extension: " + value));
//                                filePropertyDetailsResolver.resolveSize(filepath).ifPresent((value) -> LOGGER.info("File Size: " + value));
//                                filePropertyDetailsResolver.resolveCreationTime(filepath).ifPresent((value) -> LOGGER.info("File Creation Date: " + value));
//                                filePropertyDetailsResolver.resolveModificationTime(filepath).ifPresent((value) -> LOGGER.info("File Modification Date: " + value));

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

                                FileModel newEntry = fileService.createFileEntry(fileEntry);

                                LOGGER.info("Entry created: "+ newEntry.toString());

                                MonitorResponse response = MonitorResponse.newBuilder()
                                        .setEntryId(newEntry.getId())
                                        .setFilename(newEntry.getFilename())
                                        .setEventKind(newEntry.getEventKind())
                                        .setOperationDateTme(newEntry.getOperationDateTme().toString())
                                        .setFileDetailsId(newEntry.getFileDetails().getId())
                                        .setExtension(newEntry.getFileDetails().getExtension())
                                        .setSize(newEntry.getFileDetails().getSize())
                                        .setCreationDate(newEntry.getFileDetails().getCreationDate().toString())
                                        .setModificationDate(newEntry.getFileDetails().getModificationDate().toString())
                                        .build();

                                responseObserver.onNext(response);

                            } catch (RuntimeException e) {
                                LOGGER.error(e);
                            }
                        } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                            LOGGER.info("Deleted: " + filename);

                            FileModel fileEntry = new FileModel();
                            fileEntry.setFilename(String.valueOf(filename));
                            fileEntry.setEventKind("ENTRY_DELETE");
                            fileEntry.setOperationDateTme(Instant.now());

                            LOGGER.info("Entry created: "+ fileService.createFileEntryNoDetails(fileEntry).toString());
                        }

                        boolean valid = key.reset();
                        if (!valid) {
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
    }
}
