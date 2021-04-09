package com.sisw.alexpopa.folderhistorymonitor.repository;

import com.sisw.alexpopa.folderhistorymonitor.model.FileDetailsModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Alex Daniel Popa
 */
@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileDetailsRepositoryIntegrationTest {

    @Autowired
    private FileDetailsRepository fileDetailsRepository;

    @BeforeAll
    public void setup() {
        fileDetailsRepository.deleteAll();
    }

    @AfterAll
    public void done() {
        fileDetailsRepository.deleteAll();
    }

    @Test
    public void giventFileDetails_whenFindByFileDetailsId_thenReturnFileDetails() {
        // given
        FileDetailsModel fileDetails = this.fileDetailsRepository.save(new FileDetailsModel(".ext",1L, Instant.now(), Instant.now()));


        // when
        Optional<FileDetailsModel> found = this.fileDetailsRepository.findById(fileDetails.getId());

        // then
        assertTrue(found.isPresent());
        assertEquals(fileDetails.getId(),found.get().getId());

    }

}
