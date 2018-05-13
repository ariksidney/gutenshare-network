package com.group4.core;

import com.google.common.base.Preconditions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * This class represents a real world course from a school.
 *
 * @author Arik Sidney Guggenheim
 * @version 1.0
 */

@Entity
@Table(name = "T_Course")
public class Course {

    @Id
    @Column(name = "course_id", nullable = false, unique = true)
    private String courseId;

    @Column(name = "name")
    private String name;


    /**
     * Empty constructor needed for JPA
     */
    Course() {
    }


    /**
     * Constructor to create an instance of Course based on a CourseBuilder.
     *
     * @param builder Instance of CourseBuilder
     */
    Course(CourseBuilder builder) {
        this.courseId = IdGenerator.timeBasedUUID().toString();
        this.name = Preconditions.checkNotNull(builder.name);
    }

    public String getName() {
        return name;
    }

    /**
     * Generated equals method, based on course name
     *
     * @param o instance of Course
     * @return if courses are equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(getName(), course.getName());
    }

    /**
     * Generated hashCode method, generates hash based on courseId
     *
     * @return hash as int
     */
    @Override
    public int hashCode() {

        return Objects.hash(courseId, getName());
    }

    /**
     * ToString implemantation which just returns the course name
     *
     * @return course as string
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Implementation of builder pattern to create an instance of a course.
     *
     * @author Arik Sidney Guggenheim
     * @version 1.0
     */
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

