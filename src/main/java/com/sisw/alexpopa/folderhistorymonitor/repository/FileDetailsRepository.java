package com.sisw.alexpopa.folderhistorymonitor.repository;

import com.sisw.alexpopa.folderhistorymonitor.model.FileDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Alex Daniel Popa
 */
public interface FileDetailsRepository extends JpaRepository<FileDetailsModel, Long> {
}
