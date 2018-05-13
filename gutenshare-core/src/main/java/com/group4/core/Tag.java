package com.group4.core;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.util.List;

/**
 * This class represents a single Tag. Multiple tags can be given to a document to make it better searchable.
 *
 * @author Arik Sidney Guggenheim
 * @version 1.0
 */
@Entity
@Table(name = "T_TAG")
public class Tag {

    @Id
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Document> documents;

    /**
     * Empty constructor needed for JPA
     */
    protected Tag() {
        // For JPA
    }

    /**
     * Constructor to create an instance of Tag based on a TagBuilder.
     *
     * @param builder Instance of TagBuilder
     */
    Tag(TagBuilder builder) {
        this.name = Preconditions.checkNotNull(builder.name);
    }

    public String getName() {
        return name;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public static class TagBuilder {
        private String name;

        public TagBuilder setName(String name) {
            this.name = name.toUpperCase();
            return this;
        }

        public Tag build() {
            return new Tag(this);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
