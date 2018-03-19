package com.group4.api;

import com.group4.core.Document;

import java.io.InputStream;

public class DocumentDto {

    private String title;
    private String filetype;
    private InputStream documentStream;

    public DocumentDto(String title, String filetype, InputStream documentStream) {
        this.title = title;
        this.filetype = filetype;
        this.documentStream = documentStream;
    }

    public String getTitle() {
        return title;
    }

    public String getFiletype() {
        return filetype;
    }

    public InputStream getDocumentStream() {
        return documentStream;
    }
}
