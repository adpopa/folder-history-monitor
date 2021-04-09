package com.sisw.alexpopa.folderhistorymonitor.service;

import com.sisw.alexpopa.folderhistorymonitor.model.FileDetailsModel;
import com.sisw.alexpopa.folderhistorymonitor.repository.FileDetailsRepository;
import com.sisw.alexpopa.folderhistorymonitor.service.impl.FileDetailsServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Alex Daniel Popa
 */
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileDetailsServiceImplUnitTest {

    @TestConfiguration
    public class FileDetailsServiceImplTestContextConfiguration {
        @Bean
        public FileDetailsService fileDetailsService() {
            return new FileDetailsServiceImpl();
        }
    }

    @Autowired
    private FileDetailsService fileDetailsService;

    @MockBean
    private FileDetailsRepository fileDetailsRepository;

    private FileDetailsModel fileDetails = null;

    @BeforeAll
    public void setup() {
        fileDetails = new FileDetailsModel(".ext",10000L, Instant.now(), Instant.now());
        Long id = 1L;
        Mockito.when(fileDetailsRepository.save(fileDetails)).thenReturn(fileDetails);
        Mockito.when(fileDetailsRepository.findById(id)).thenReturn(Optional.of(fileDetails));
    }

    @Test
    public void whenInsertFileDetails_thenFileDetailsShouldBeReturned() {
        // when
        FileDetailsModel returned = fileDetailsService.createFileDetails(fileDetails);

        // then
        assertEquals(fileDetails.getSize(),returned.getSize());
        assertEquals(fileDetails.getExtension(),returned.getExtension());
        assertEquals(fileDetails.getCreationDate(),returned.getCreationDate());
        assertEquals(fileDetails.getModificationDate(),returned.getModificationDate());
    }

}
