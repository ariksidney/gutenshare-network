package com.group4.core;

import com.google.common.base.Preconditions;

import java.util.List;

public class School {

    private String name;
    private List<Department> departments;

    School(SchoolBuilder builder) {
        this.name = Preconditions.checkNotNull(builder.name);
    }

    public String getName() {
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

