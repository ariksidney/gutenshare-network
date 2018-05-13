package com.group4.core;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface to interact with course database table.
 *
 * @author Arik Sidney Guggenheim
 * @version 1.0
 */
public interface CourseJpaRepositoryInterface extends JpaRepository<Course, Integer> {

    /**
     * Checks if a course with the given name already exists.
     *
     * @param name Name of course to check
     * @return if course exists or not
     */
    boolean existsByName(String name);

    /**
     * Method to get a course instance based on a cocurse name
     *
     * @param name Name of course to get
     * @return Course object
     */
    Course getByName(String name);
}
