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

    @Column(name = "filetype", nullable = false)
    private String filetype;

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

    protected Document() {
        // For JPA
    }

    Document(DocumentBuilder documentBuilder) {
        this.id = IdGenerator.timeBasedUUID().toString();
        this.title = Preconditions.checkNotNull(documentBuilder.title);
        this.filetype = Preconditions.checkNotNull(documentBuilder.filetype);
        this.uploadDate = LocalDateTime.now();
        this.tags = documentBuilder.tags;
        this.inputStream = documentBuilder.inputStream;
    }

    public String getTitle() {
        return title;
    }

    public Path getPathToFile() {
        return Paths.get(pathToFile);
    }

    public String getFiletype() {
        return filetype;
    }

    public void storeFile(DocumentStoreRepositoryInterface documentStoreRepositoryInterface) {
        this.pathToFile = documentStoreRepositoryInterface.storeDocument(this, this.inputStream).toString();
    }

    public String createFilename() {
        return String.format(String.format("%s.%s", this.id.substring(0, 8), this.filetype));
    }

    public static class DocumentBuilder {
        private String title;
        private String filetype;
        private List<Tag> tags;
        private InputStream inputStream;

        public DocumentBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public DocumentBuilder setFiletype(String filetype) {
            this.filetype = filetype;
            return this;
        }

        public DocumentBuilder setTags(List<Tag> tags) {
            this.tags = tags;
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
