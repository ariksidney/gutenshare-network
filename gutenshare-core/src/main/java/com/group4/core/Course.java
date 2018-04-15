package com.group4.core;

import com.google.common.base.Preconditions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "T_Course")
public class Course {

    @Id
    @Column(name = "course_id", nullable = false, unique = true)
    private String courseId;

    @Column(name = "name")
    private String name;

    Course() {
        // For JPA
    }

    Course(CourseBuilder builder) {
        this.courseId = IdGenerator.timeBasedUUID().toString();
        this.name = Preconditions.checkNotNull(builder.name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId) &&
                Objects.equals(getName(), course.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(courseId, getName());
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

