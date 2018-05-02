package com.group4.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DeliverDocumentDto extends DocumentDto {

    private byte[] documentAsBytes;
    private LocalDateTime uploadDate;

    public DeliverDocumentDto(String id, String title, String documentType, Optional<String> school, Optional<String>
            department, Optional<String> course, String fileType, Optional<List<String>> tags, Optional<String>
            description, LocalDateTime uploadDate, byte[] documentAsBytes) {
        super(id, title, documentType, school, department, course, fileType, tags, description);
        this.documentAsBytes = documentAsBytes;
        this.uploadDate = uploadDate;
    }

    public byte[] getDocumentAsBytes() {
        return documentAsBytes;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }
}
