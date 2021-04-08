package com.sisw.alexpopa.folderhistorymonitor.service.impl;

import com.sisw.alexpopa.folderhistorymonitor.model.FileModel;
import com.sisw.alexpopa.folderhistorymonitor.repository.FileRepository;
import com.sisw.alexpopa.folderhistorymonitor.service.FileDetailsService;
import com.sisw.alexpopa.folderhistorymonitor.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Alex Daniel Popa
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileDetailsService fileDetailsService;

    @Override
    public FileModel createFileEntry(FileModel fileEntry) {

        fileEntry.setFileDetails(this.fileDetailsService.createFileDetails(fileEntry.getFileDetails()));

        return fileRepository.save(fileEntry);
    }

    @Override
    public FileModel createFileEntryNoDetails(FileModel fileEntry) {
        return fileRepository.save(fileEntry);
    }
}
