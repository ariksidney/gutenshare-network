package com.group4.core;

import com.google.common.base.Preconditions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "T_DEPARTMENT")
public class Department {

    @Id
    @Column(name = "department_id", unique = true, nullable = false)
    private String departmentId;

    @Column(name = "name", nullable = false)
    private String name;


    Department() {
        // For JPA
    }

    Department(DepartmentBuilder builder) {
        this.departmentId = IdGenerator.timeBasedUUID().toString();
        this.name = Preconditions.checkNotNull(builder.name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
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

