package com.sisw.alexpopa.folderhistorymonitor.repository;

import com.sisw.alexpopa.folderhistorymonitor.model.FileDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alex Daniel Popa
 */
@Repository
public interface FileDetailsRepository extends JpaRepository<FileDetailsModel, Long> {
}
