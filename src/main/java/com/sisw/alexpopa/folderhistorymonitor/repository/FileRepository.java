package com.sisw.alexpopa.folderhistorymonitor.repository;

import com.sisw.alexpopa.folderhistorymonitor.model.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alex Daniel Popa
 */
@Repository
public interface FileRepository extends JpaRepository<FileModel, Long> {
}
