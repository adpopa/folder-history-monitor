package com.sisw.alexpopa.folderhistorymonitor.service.impl;

import com.sisw.alexpopa.folderhistorymonitor.model.FileDetailsModel;
import com.sisw.alexpopa.folderhistorymonitor.repository.FileDetailsRepository;
import com.sisw.alexpopa.folderhistorymonitor.service.FileDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Alex Daniel Popa
 */
@Service
public class FileDetailsServiceImpl implements FileDetailsService {

    @Autowired
    private FileDetailsRepository fileDetailsRepository;

    @Override
    public FileDetailsModel createFileDetails(FileDetailsModel fileDetails) {
        return fileDetailsRepository.save(fileDetails);
    }
}
