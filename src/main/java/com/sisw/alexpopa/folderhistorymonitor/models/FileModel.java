package com.sisw.alexpopa.folderhistorymonitor.models;

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
    private Long _id;

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

    /**
     *
     * @param filename
     * @param eventKind
     * @param operationDateTme
     */
    public FileModel(String filename, String eventKind, Instant operationDateTme) {
        this.filename = filename;
        this.eventKind = eventKind;
        this.operationDateTme = operationDateTme;
    }

    @Override
    public String toString() {
        return "FileModel{" +
                "_id=" + _id +
                ", filename='" + filename + '\'' +
                ", eventKind='" + eventKind + '\'' +
                ", operationDateTme=" + operationDateTme +
                '}';
    }
}
