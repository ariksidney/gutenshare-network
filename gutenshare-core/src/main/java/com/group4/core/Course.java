package com.group4.core;

import com.google.common.base.Preconditions;

public class Course {

    private String name;

    Course(CourseBuilder builder) {
        this.name = Preconditions.checkNotNull(builder.name);
    }

    public String getName() {
        return name;
    }


    public static class CourseBuilder {
        private String name;

        public CourseBuilder setName(String name) {
            this.name = name.toLowerCase();
            return this;
        }

        public Course build() {
            return new Course(this);
        }
    }
}

