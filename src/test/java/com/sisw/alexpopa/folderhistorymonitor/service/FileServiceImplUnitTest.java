package com.sisw.alexpopa.folderhistorymonitor.service;

import com.sisw.alexpopa.folderhistorymonitor.model.FileDetailsModel;
import com.sisw.alexpopa.folderhistorymonitor.model.FileModel;
import com.sisw.alexpopa.folderhistorymonitor.repository.FileDetailsRepository;
import com.sisw.alexpopa.folderhistorymonitor.repository.FileRepository;
import com.sisw.alexpopa.folderhistorymonitor.service.impl.FileDetailsServiceImpl;
import com.sisw.alexpopa.folderhistorymonitor.service.impl.FileServiceImpl;
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
public class FileServiceImplUnitTest {

    @TestConfiguration
    public class FileServiceImplTestContextConfiguration {
        @Bean
        public FileService fileDetailsService() {
            return new FileServiceImpl();
        }
    }

    @Autowired
    private FileService fileService;

    @MockBean
    private FileRepository fileRepository;

    private FileModel fileModel = null;

    @BeforeAll
    public void setup() {
        FileDetailsModel fileDetails = new FileDetailsModel(".ext",10000L, Instant.now(), Instant.now());
        fileModel = new FileModel("filename","ENTRY_CREATE",Instant.now(), fileDetails);

        Long id = 1L;
        Mockito.when(fileRepository.save(fileModel)).thenReturn(fileModel);
        Mockito.when(fileRepository.findById(id)).thenReturn(Optional.of(fileModel));
    }

    @Test
    public void whenInsertFileDetails_thenFileDetailsShouldBeReturned() {
        // when
        FileModel returned = fileService.createFileEntry(fileModel);

        // then
        assertEquals(fileModel.getFilename(),returned.getFilename());
        assertEquals(fileModel.getEventKind(),returned.getEventKind());
        assertEquals(fileModel.getOperationDateTme(),returned.getOperationDateTme());
        assertEquals(fileModel.getFileDetails(),returned.getFileDetails());
    }

}
