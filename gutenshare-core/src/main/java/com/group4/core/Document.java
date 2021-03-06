package com.group4.core;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Represents a document which is uploaded by a registered user of the gutenshare network.
 *
 * @author Arik Sidney Guggenheim
 * @version 1.0
 */

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;

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

    /**
     * Empty constructor needed for JPA
     */
    protected Document() {
        // For JPA
    }

    /**
     * Constructor to create an instance of Document based on a DocumentBuilder.
     *
     * @param documentBuilder Instance of DocumentBuilder
     */
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
        this.user = documentBuilder.user;
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

    public User getUser() {
        return user;
    }

    /**
     * This method stores the document which it is called on. The method doesn't care where the file is stored,
     * because the implementation of the storage is given as an imput parameter.
     *
     * @param documentStoreRepositoryInterface Implementation of the documentStoreRepositoryInterface to decide where to
     *                                         store the document.
     */
    public void storeFile(DocumentStoreRepositoryInterface documentStoreRepositoryInterface) {
        this.pathToFile = documentStoreRepositoryInterface.storeDocument(
                this, this.inputStream).toString();
    }

    /**
     * Gets the document from the selected storage source and returns it as a byte array.
     *
     * @param documentStoreRepositoryInterface Implementation of the documentStoreRepositoryInterface to decide where
     *                                         to get the document from.
     * @return document as byte array
     */
    public byte[] getContent(DocumentStoreRepositoryInterface documentStoreRepositoryInterface) {
        return documentStoreRepositoryInterface.getDocument(this);
    }

    /**
     * Creates an unique filename based on the document id. It is necessary to do this in the case that files with the
     * same names are uploaded.
     *
     * @return String with the generated filename
     */
    public String createFilename() {
        return String.format(String.format("%s.%s", this.id.substring(0, 8), this.fileType));
    }

    /**
     * Implementation of builder pattern to create an instance of a Document.
     *
     * @author Arik Sidney Guggenheim
     * @version 1.0
     */
    public static class DocumentBuilder {
        private String title;
        private DocumentType documentType;
        private School school;
        private Department department;
        private Course course;
        private String fileType;
        private List<Tag> tags;
        private String description;
        private Set<Comment> comments;
        private InputStream inputStream;
        private User user;

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

        public DocumentBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public Document build() {
            return new Document(this);
        }
    }
}
