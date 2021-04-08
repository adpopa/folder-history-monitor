package com.sisw.alexpopa.folderhistorymonitor.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author Alex Daniel Popa
 */
@Entity(name = "file_details")
@Table(name = "file_details")
public class FileDetailsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long _id;

    @Getter
    @Setter
    private String extension;

    @Getter
    @Setter
    private Long size;

    @Getter
    @Setter
    private Instant creationDate;

    @Getter
    @Setter
    private Instant modificationDate;

    public FileDetailsModel() {

    }

    /**
     *
     * @param size
     * @param creationDate
     * @param modificationDate
     */
    public FileDetailsModel(Long size, Instant creationDate, Instant modificationDate) {
        this.size = size;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    @Override
    public String toString() {
        return "FileDetailsModel{" +
                "_id=" + _id +
                ", extension='" + extension + '\'' +
                ", size=" + size +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
