package com.group4.api;

import java.util.List;
import java.util.Optional;

public class DeliverDocumentDto extends DocumentDto {

    private byte[] documentAsBytes;

    public DeliverDocumentDto(String title, String documentType, Optional<String> school, Optional<String>
            department, Optional<String> course, String fileType, Optional<List<String>> tags, Optional<String>
            description, byte[] documentAsBytes) {
        super(title, documentType, school, department, course, fileType, tags, description);
        this.documentAsBytes = documentAsBytes;
    }

    public byte[] getDocumentAsBytes() {
        return documentAsBytes;
    }
}
