package com.group4.core;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_SCHOOL")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "department")
    private List<Document> documents;

    School(SchoolBuilder builder) {
        this.name = Preconditions.checkNotNull(builder.name);
    }

    public String getName() {
        return name;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    School() {
        // For JPA
    }

    public static class SchoolBuilder {
        private String name;

        public SchoolBuilder setName(String name) {
            this.name = name.toLowerCase();
            return this;
        }

        public School build() {
            return new School(this);
        }
    }
}

