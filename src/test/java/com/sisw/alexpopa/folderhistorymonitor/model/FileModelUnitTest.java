package com.sisw.alexpopa.folderhistorymonitor.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Alex Daniel Popa
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileModelUnitTest {

    private FileDetailsModel fileDetails = null;

    @BeforeAll
    public void setup() {
        fileDetails = new FileDetailsModel(".ext",10000L, Instant.now(), Instant.now());
    }

    @Test
    public void testAccessorsMutators() {

        FileModel fileModel = new FileModel("filename","ENTRY_CREATE",Instant.now(),this.fileDetails);

        assertEquals("filename",fileModel.getFilename());
        assertEquals("ENTRY_CREATE",fileModel.getEventKind());
//        assertTrue(Instant.now().isAfter(fileModel.getOperationDateTme()));
        assertEquals(fileDetails,fileModel.getFileDetails());

    }

}
