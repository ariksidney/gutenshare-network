package com.group4.core;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_TAG")
public class Tag {

    @Id
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Document> documents;

    protected Tag() {
        // For JPA
    }

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
}
