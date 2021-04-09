package com.sisw.alexpopa.folderhistorymonitor.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Alex Daniel Popa
 */
public class FileDetailsModelUnitTest {

    @Test
    public void testAccessorsMutators() {

        FileDetailsModel fileDetails = new FileDetailsModel(".ext",10000L,Instant.now(),Instant.now());

        assertEquals(".ext",fileDetails.getExtension());
        assertTrue(10000L == fileDetails.getSize());
        assertTrue(Instant.now().isAfter(fileDetails.getCreationDate()));
        assertTrue(Instant.now().isAfter(fileDetails.getModificationDate()));

    }

}
