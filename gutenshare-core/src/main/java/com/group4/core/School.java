package com.group4.core;

import com.google.common.base.Preconditions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * This class represents a real world school.
 *
 * @author Arik Sidney Guggenheim
 * @version 1.0
 */
@Entity
@Table(name = "T_SCHOOL")
public class School {

    @Id
    @Column(name = "school_id", nullable = false, unique = true)
    private String schoolId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;


    /**
     * Constructor to create an instance of School based on a SchoolBuilder.
     *
     * @param builder Instance of SchoolBuilder
     */
    School(SchoolBuilder builder) {
        this.schoolId = IdGenerator.timeBasedUUID().toString();
        this.name = Preconditions.checkNotNull(builder.name);
    }

    /**
     * Empty constructor needed for JPA
     */
    School() {
        // For JPA
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof School)) return false;
        School school = (School) o;
        return Objects.equals(getName(), school.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(schoolId, getName());
    }

    @Override
    public String toString() {
        return name;
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

