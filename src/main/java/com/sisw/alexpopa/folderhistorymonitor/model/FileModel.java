package com.sisw.alexpopa.folderhistorymonitor.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author Alex Daniel Popa
 */
@Entity(name = "file_history")
@Table(name = "file_history")
public class FileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String filename;

    @Column(nullable = false)
    @Getter
    @Setter
    private String eventKind;

    @Column(nullable = false)
    @Getter
    @Setter
    private Instant operationDateTme;

    @OneToOne
    @JoinColumn(name = "fileDetails_id", unique = true)
    @Getter
    @Setter
    private FileDetailsModel fileDetails;

    public FileModel() {
    }

    public FileModel(String filename, String eventKind, Instant operationDateTme, FileDetailsModel fileDetails) {
        this.filename = filename;
        this.eventKind = eventKind;
        this.operationDateTme = operationDateTme;
        this.fileDetails = fileDetails;
    }

    @Override
    public String toString() {
        String str;

        str = "FileModel{" +
                "_id=" + id +
                ", filename='" + filename + '\'' +
                ", eventKind='" + eventKind + '\'' +
                ", operationDateTme=" + operationDateTme;

        if(fileDetails == null) {
            return str + ", fileDetails=" + '}';
        }

        return str + ", fileDetails=" + fileDetails.toString() + '}';
    }
}
