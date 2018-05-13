package com.group4.core;

import com.google.common.base.Preconditions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * This class represents a real world department from a school.
 *
 * @author Arik Sidney Guggenheim
 * @version 1.0
 */

@Entity
@Table(name = "T_DEPARTMENT")
public class Department {

    @Id
    @Column(name = "department_id", unique = true, nullable = false)
    private String departmentId;

    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Empty constructor needed for JPA
     */
    Department() {
        // For JPA
    }

    /**
     * Constructor to create an instance of a Department based on a DepartmentBuilder.
     *
     * @param builder Instance of DepartmentBuilder
     */
    Department(DepartmentBuilder builder) {
        this.departmentId = IdGenerator.timeBasedUUID().toString();
        this.name = Preconditions.checkNotNull(builder.name);
    }

    public String getName() {
        return name;
    }

    /**
     * Generated equals method, based on department name
     *
     * @param o instance of Department
     * @return if deparments are equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return Objects.equals(getName(), that.getName());
    }

    /**
     * Generated hashCode method, generates hash based on department name
     *
     * @return hash as int
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }


    /**
     * ToString implemantation which just returns the department name
     *
     * @return department as string
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Implementation of builder pattern to create an instance of a department.
     *
     * @author Arik Sidney Guggenheim
     * @version 1.0
     */
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

