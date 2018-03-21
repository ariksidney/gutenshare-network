package com.group4.api;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class DocumentDto {

    private String title;
    private String documentType;
    private String fileType;
    private Optional<List<String>> tags;
    private String description;
    private InputStream documentStream;

    public DocumentDto(
            String title,
            String documentType,
            String fileType,
            Optional<List<String>> tags,
            String description,
            InputStream documentStream
    ) {

        this.title = title;
        this.documentType = documentType;
        this.fileType = fileType;
        this.tags = tags;
        this.description = description;
        this.documentStream = documentStream;
    }

    public String getTitle() {
        return title;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getFileType() {
        return fileType;
    }

    public Optional<List<String>> getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public InputStream getDocumentStream() {
        return documentStream;
    }
}
