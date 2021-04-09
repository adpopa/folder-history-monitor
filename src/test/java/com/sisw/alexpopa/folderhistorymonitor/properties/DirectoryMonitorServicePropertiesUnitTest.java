package com.sisw.alexpopa.folderhistorymonitor.properties;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alex Daniel Popa
 */
public class DirectoryMonitorServicePropertiesUnitTest {

    @Test
    public void testAccessorsMutators() {
        DirectoryMonitorServiceProperties dmsp = new DirectoryMonitorServiceProperties();

        dmsp.setDirectoryPath("pathToDir");

        assertEquals("pathToDir", dmsp.getDirectoryPath());

    }

    @Test
    public void whenNullPointerException_thenAssertionSucceeds() {
        DirectoryMonitorServiceProperties dmsp = new DirectoryMonitorServiceProperties();

        Exception exception = assertThrows(NullPointerException.class, () -> dmsp.setDirectoryPath(null));

        String expectedMessage = "Value cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
