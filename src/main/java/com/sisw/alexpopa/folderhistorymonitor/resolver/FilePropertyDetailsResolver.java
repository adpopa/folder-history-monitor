package com.sisw.alexpopa.folderhistorymonitor.resolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Optional;

/**
 * @author Alex Daniel Popa
 */
public class FilePropertyDetailsResolver {

    public Optional<String> resolveExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public Optional<Long> resolveSize(Path path) {
        try {
            long size = (long) Files.getAttribute(path, "size");

            return Optional.ofNullable(size);
        } catch (IOException e) {
            throw new RuntimeException("An issue occured when resolving size", e);
        }
    }

    public Optional<Instant> resolveCreationTime(Path path) {
        try {
            FileTime creationTime = (FileTime) Files.getAttribute(path, "creationTime");

            return Optional.ofNullable(creationTime).map((FileTime::toInstant));
        } catch (IOException e) {
            throw new RuntimeException("An issue occured when resolving creation time", e);
        }
    }

    public Optional<Instant> resolveModificationTime(Path path) {
        try {
            FileTime creationTime = (FileTime) Files.getAttribute(path, "lastModifiedTime");

            return Optional.ofNullable(creationTime).map((FileTime::toInstant));
        } catch (IOException e) {
            throw new RuntimeException("An issue occured when resolving modification time", e);
        }
    }
}
