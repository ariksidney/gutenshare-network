package com.group4.core;

import com.google.common.base.Preconditions;

import java.util.List;

public class Department {

    private String name;
    private List<Course> courses;

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

