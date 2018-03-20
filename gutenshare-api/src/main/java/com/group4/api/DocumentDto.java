package com.group4.api;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class DocumentDto {

    private String title;
    private String filetype;
    private Optional<List<String>> tags;
    private InputStream documentStream;

    public DocumentDto(String title, String filetype, Optional<List<String>> tags, InputStream documentStream) {
        this.title = title;
        this.filetype = filetype;
        this.tags = tags;
        this.documentStream = documentStream;
    }

    public String getTitle() {
        return title;
    }

    public String getFiletype() {
        return filetype;
    }

    public Optional<List<String>> getTags() {
        return tags;
    }

    public InputStream getDocumentStream() {
        return documentStream;
    }
}
