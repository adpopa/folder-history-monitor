package com.sisw.alexpopa.folderhistorymonitor.model;

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
    private Long id;

    private String filename;

    @Column(nullable = false)
    private String eventKind;

    @Column(nullable = false)
    private Instant operationDateTme;

    @OneToOne
    @JoinColumn(name = "fileDetails_id", unique = true)
    private FileDetailsModel fileDetails = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getEventKind() {
        return eventKind;
    }

    public void setEventKind(String eventKind) {
        this.eventKind = eventKind;
    }

    public Instant getOperationDateTme() {
        return operationDateTme;
    }

    public void setOperationDateTme(Instant operationDateTme) {
        this.operationDateTme = operationDateTme;
    }

    public FileDetailsModel getFileDetails() {
        return fileDetails;
    }

    public void setFileDetails(FileDetailsModel fileDetails) {
        this.fileDetails = fileDetails;
    }

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
