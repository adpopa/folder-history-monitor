package com.sisw.alexpopa.folderhistorymonitor.service;

import com.sisw.alexpopa.folderhistorymonitor.model.FileModel;

/**
 * @author Alex Daniel Popa
 */
public interface FileService {

    public FileModel createFileEntry(FileModel fileEntry);

    public FileModel createFileEntryNoDetails(FileModel fileEntry);

 }
