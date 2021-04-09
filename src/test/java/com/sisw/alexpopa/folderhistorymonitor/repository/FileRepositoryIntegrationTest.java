package com.sisw.alexpopa.folderhistorymonitor.repository;

import com.sisw.alexpopa.folderhistorymonitor.model.FileDetailsModel;
import com.sisw.alexpopa.folderhistorymonitor.model.FileModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Alex Daniel Popa
 */
@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileRepositoryIntegrationTest {

    private FileDetailsModel fileDetails;

    @Autowired
    private FileRepository fileRepository;

    @BeforeAll
    public void setup() {
        fileRepository.deleteAll();
        FileDetailsModel fileDetails = new FileDetailsModel(".ext",1L, Instant.now(), Instant.now());
    }

    @AfterAll
    public void done() {
        fileRepository.deleteAll();
    }

    @Test
    public void giventFile_whenFindByFileId_thenReturnFile() {
        // given
        FileDetailsModel fileDetails = new FileDetailsModel();
        FileModel fileEntry = new FileModel("filename","ENTRY_CREATE",Instant.now(),fileDetails);
        fileEntry = this.fileRepository.save(fileEntry);

        // when
        FileModel found = this.fileRepository.findById(fileEntry.getId()).get();

        // then
        assertEquals(fileEntry.getId(),found.getId());

    }

}
