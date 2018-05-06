package com.group4.api;

import com.group4.core.User;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class CreateDocumentDto extends DocumentDto {

    private InputStream inputStream;

    public CreateDocumentDto(String id, String title, String documentType, Optional<String> school, Optional<String> department,
                             Optional<String> course, String fileType, Optional<List<String>> tags, Optional<String>
                                     description, InputStream inputStream, User user) {
        super(id, title, documentType, school, department, course, fileType, tags, description, user);
        this.inputStream = inputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
