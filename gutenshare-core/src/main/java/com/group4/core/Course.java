package com.group4.core;

import com.google.common.base.Preconditions;

import javax.persistence.*;

@Entity
@Table(name = "T_Course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    Course() {
        // For JPA
    }

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

