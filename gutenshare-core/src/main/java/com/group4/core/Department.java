package com.group4.core;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_DEPARTMENT")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;


    @OneToMany(mappedBy = "department")
    private List<Document> documents;

    Department() {
        // For JPA
    }

    Department(DepartmentBuilder builder) {
        this.name = Preconditions.checkNotNull(builder.name);
    }

    public String getName() {
        return name;
    }

    public static class DepartmentBuilder {
        private String name;

        public DepartmentBuilder setName(String name) {
            this.name = name.toLowerCase();
            return this;
        }

        public Department build() {
            return new Department(this);
        }
    }
}

