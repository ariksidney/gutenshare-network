package com.group4.core;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "T_DOCUMENT")
public class Document {

    @Id
    @Column(name = "document_id", nullable = false, unique = true)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "path_to_file", nullable = false)
    private String pathToFile;

    @Column(name = "documenttype", nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "filetype", nullable = false)
    private String fileType;

    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;

    private transient InputStream inputStream;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "T_TAG_DOCUMENT",
            joinColumns = @JoinColumn(name = "document_id", referencedColumnName = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_name", referencedColumnName = "name"))
    private List<Tag> tags;

    @Column(name = "description", nullable = true)
    private String description;

    protected Document() {
        // For JPA
    }

    Document(DocumentBuilder documentBuilder) {
        this.id = IdGenerator.timeBasedUUID().toString();
        this.title = Preconditions.checkNotNull(documentBuilder.title);
        this.documentType = Preconditions.checkNotNull(documentBuilder.documentType);
        this.school = documentBuilder.school;
        this.course = documentBuilder.course;
        this.department = documentBuilder.department;
        this.fileType = Preconditions.checkNotNull(documentBuilder.fileType);
        this.uploadDate = LocalDateTime.now();
        this.tags = documentBuilder.tags;
        this.description = documentBuilder.description;
        this.inputStream = documentBuilder.inputStream;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Path getPathToFile() {
        return Paths.get(pathToFile);
    }

    public String getFileType() {
        return fileType;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public School getSchool() {
        return school;
    }

    public Department getDepartment() {
        return department;
    }

    public Course getCourse() {
        return course;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void storeFile(DocumentStoreRepositoryInterface documentStoreRepositoryInterface) {
        this.pathToFile = documentStoreRepositoryInterface.storeDocument(
                this, this.inputStream).toString();
    }

    public byte[] getContent(DocumentStoreRepositoryInterface documentStoreRepositoryInterface) {
        return documentStoreRepositoryInterface.getDocument(this);
    }

    public String createFilename() {
        return String.format(String.format("%s.%s", this.id.substring(0, 8), this.fileType));
    }

    public static class DocumentBuilder {
        private String title;
        private DocumentType documentType;
        private School school;
        private Department department;
        private Course course;
        private String fileType;
        private List<Tag> tags;
        private String description;
        private InputStream inputStream;

        public DocumentBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public DocumentBuilder setDocumentType(DocumentType documentType) {
            this.documentType = documentType;
            return this;
        }

        public DocumentBuilder setSchool(School school) {
            this.school = school;
            return this;
        }

        public DocumentBuilder setDepartment(Department department) {
            this.department = department;
            return this;
        }

        public DocumentBuilder setCourse(Course course) {
            this.course = course;
            return this;
        }

        public DocumentBuilder setFileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public DocumentBuilder setTags(List<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public DocumentBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public DocumentBuilder setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public Document build() {
            return new Document(this);
        }
    }
}
