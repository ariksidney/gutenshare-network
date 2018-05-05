package com.group4.api;

import com.group4.core.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DeliverDocumentDto extends DocumentDto {

    private byte[] documentAsBytes;
    private LocalDateTime uploadDate;
    private List<Comment> comments;

    public DeliverDocumentDto(String id, String title, String documentType, Optional<String> school, Optional<String>
            department, Optional<String> course, String fileType, Optional<List<String>> tags, Optional<String>
                                      description, LocalDateTime uploadDate, List<Comment> comments, byte[]
            documentAsBytes) {
        super(id, title, documentType, school, department, course, fileType, tags, description);
        this.documentAsBytes = documentAsBytes;
        this.uploadDate = uploadDate;
        this.comments = comments;
    }

    public byte[] getDocumentAsBytes() {
        return documentAsBytes;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
