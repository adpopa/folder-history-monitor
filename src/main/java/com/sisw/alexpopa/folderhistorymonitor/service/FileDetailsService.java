package com.sisw.alexpopa.folderhistorymonitor.service;

import com.sisw.alexpopa.folderhistorymonitor.model.FileDetailsModel;

import java.util.Optional;

/**
 * @author Alex Daniel Popa
 */
public interface FileDetailsService {

    public FileDetailsModel createFileDetails(FileDetailsModel fileDetails);

    public Optional<FileDetailsModel> findByFileDetailsId(Long id);
}
