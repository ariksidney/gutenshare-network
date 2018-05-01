package com.group4.api;

import java.util.List;
import java.util.Optional;

public class DocumentDto {

    private String id;
    private String title;
    private String documentType;
    private Optional<String> school;
    private Optional<String> department;
    private Optional<String> course;
    private String fileType;
    private Optional<List<String>> tags;
    private Optional<String> description;

    public DocumentDto(
            String id,
            String title,
            String documentType,
            Optional<String> school,
            Optional<String> department,
            Optional<String> course,
            String fileType,
            Optional<List<String>> tags,
            Optional<String> description
    ) {
        this.id = id;
        this.title = title;
        this.documentType = documentType;
        this.school = school;
        this.department = department;
        this.course = course;
        this.fileType = fileType;
        this.tags = tags;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDocumentType() {
        return documentType;
    }

    public Optional<String> getSchool() {
        return school;
    }

    public Optional<String> getDepartment() {
        return department;
    }

    public Optional<String> getCourse() {
        return course;
    }

    public String getFileType() {
        return fileType;
    }

    public Optional<List<String>> getTags() {
        return tags;
    }

    public Optional<String> getDescription() {
        return description;
    }
}
