package com.sisw.alexpopa.folderhistorymonitor.service;

import com.sisw.alexpopa.folderhistorymonitor.model.FileDetailsModel;
import com.sisw.alexpopa.folderhistorymonitor.model.FileModel;
import com.sisw.alexpopa.folderhistorymonitor.resolver.FilePropertyDetailsResolver;
import com.sisw.alexpopa.grpc.DirectoryMonitorService.*;
import com.sisw.alexpopa.grpc.MonitorServiceGrpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * @author Alex Daniel Popa
 */
@GrpcService
public class MonitorGrpcService extends MonitorServiceImplBase {

    private static final Logger LOGGER = LogManager.getLogger(MonitorGrpcService.class);

    private WatchService watcher = null;
    private FilePropertyDetailsResolver filePropertyDetailsResolver = null;

    private String dirPath = null;
    private boolean startRecoding = false;
    private List<FileModel> entryList = new ArrayList<FileModel>();

    @Autowired
    private FileService fileService;

    @Override
    public void startMonitor(StartMonitorRequest request, StreamObserver<StartMonitorResponse> responseObserver) {
        dirPath = request.getMsgStartMonitorRequest(); //should be ./MonitoredFolder/
        LOGGER.info("StartMonitorRequest:" + dirPath);

        StartMonitorResponse.Builder response = StartMonitorResponse.newBuilder();
        response.setMsgStartMonitorResponse("Monitor started");
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();

        startFolderMonitor();

    }

    @Override
    public void startRecording(StartRecordingRequest request, StreamObserver<StartRecordingResponse> responseObserver) {
        if(!startRecoding)
            LOGGER.info("Started Recording...");

        startRecoding = true;

        if(!entryList.isEmpty()) {
            for(FileModel entry : entryList) {
                StartRecordingResponse.Builder response = StartRecordingResponse.newBuilder();
                response.setEntryId(entry.getId())
                                        .setFilename(entry.getFilename())
                                        .setEventKind(entry.getEventKind())
                                        .setOperationDateTme(entry.getOperationDateTme().toString())
                                        .buildPartial();

                if(entry.getFileDetails() != null) {
                    response.setFileDetailsId(entry.getFileDetails().getId())
                                        .setExtension(entry.getFileDetails().getExtension())
                                        .setSize(entry.getFileDetails().getSize())
                                        .setCreationDate(entry.getFileDetails().getCreationDate().toString())
                                        .setModificationDate(entry.getFileDetails().getModificationDate().toString());
                }

                responseObserver.onNext(response.build());
            }
            entryList.clear();
        }
        responseObserver.onCompleted();
    }

    @Override
    public void stopRecording(StopRecordingRequest request, StreamObserver<StopRecordingResponse> responseObserver) {
        super.stopRecording(request, responseObserver);
    }

    @Override
    public void stopMonitor(StopMonitorRequest request, StreamObserver<StopMonitorResponse> responseObserver) {
        String msgStop = "Stop the Monitor";
        LOGGER.info("StartMonitorRequest:" + request.getMsgStopMonitorRequest());

        StopMonitorResponse.Builder response = StopMonitorResponse.newBuilder();

        if (request.getMsgStopMonitorRequest().equals(msgStop)) {
            try {
                watcher.close();
            } catch (IOException e) {
                LOGGER.error(e);
            }
            response.setMsgStopMonitorResponse("The monitor has stopped");
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();

            LOGGER.info(response.getMsgStopMonitorResponse());
        } else {
            response.setMsgStopMonitorResponse("Error");
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();
        }


    }

    private void startFolderMonitor() {
        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            LOGGER.error(e);
        }

        if (watcher != null) {
            LOGGER.info("Started monitoring path: " + dirPath);

            Path path = Paths.get(dirPath);

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

                                LOGGER.info("Entry created: " + newEntry.toString());

                                // build list for response for client
                                if(startRecoding) {
                                    entryList.add(newEntry);
                                }

                            } catch (RuntimeException e) {
                                LOGGER.info("on CREATE file --- " + e);
                            }
                        } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                            try {
                                LOGGER.info("Deleted: " + filename);

                                FileModel fileEntry = new FileModel();
                                fileEntry.setFilename(String.valueOf(filename));
                                fileEntry.setEventKind("ENTRY_DELETE");
                                fileEntry.setOperationDateTme(Instant.now());

                                FileModel newEntry = fileService.createFileEntryNoDetails(fileEntry);

                                LOGGER.info("Entry created: " + newEntry.toString());

                                // build list for response for client
                                if(startRecoding) {
                                    entryList.add(newEntry);
                                }

                            } catch (RuntimeException e) {
                                LOGGER.error("on DELETE file --- " + e);
                            }
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

//    private void buildStartRecordingResponse(FileModel entry) {
//        entryList.add(entry);
//    }
}
