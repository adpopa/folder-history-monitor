package com.sisw.alexpopa.folderhistorymonitor.resolver;

import org.junit.jupiter.api.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Alex Daniel Popa
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FilePropertyDetailsResolverUnitTest {

    private FilePropertyDetailsResolver creationDateResolver;
    private Path path;

    @BeforeAll
    public void setup() {
        creationDateResolver = new FilePropertyDetailsResolver();
    }

    @BeforeEach
    public void init() throws Exception {
        path = Files.createTempFile("createdFile", ".txt");
        Files.write(path, "Hello World\n".getBytes(StandardCharsets.UTF_8));
    }

    @AfterEach
    public void tearDown() throws Exception{
        Files.delete(path);
    }

    @Test
    public void givenFile_whenGettingExtension_thenReturnExtension() {
        Optional<String> extenstion = creationDateResolver.resolveExtension(String.valueOf(path));

        assertEquals("txt",extenstion.get());
    }

    @Test
    public void givenFile_whenGettingSize_thenReturnSize() {
        Optional<Long> size = creationDateResolver.resolveSize(path);

        assertTrue( size.get() > 0);
    }

    @Test
    public void givenFile_whenGettingCreationTime_thenReturnDate() {
        Optional<Instant> creationTime = creationDateResolver.resolveCreationTime(path);

        creationTime.ifPresent((value) -> assertTrue(Instant.now().isAfter(value)));
    }

    @Test
    public void givenFile_whenGettingModificationTime_thenReturnDate() {
        Optional<Instant> creationTime = creationDateResolver.resolveCreationTime(path);
        Optional<Instant> modificationTime = creationDateResolver.resolveModificationTime(path);

        modificationTime.ifPresent((value) -> assertTrue(creationTime.get().isBefore(value)));
    }

}
