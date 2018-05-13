package com.group4.core;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface to interact with school database table.
 *
 * @author Arik Sidney Guggenheim
 * @version 1.0
 */
public interface SchoolJpaRepositoryInterface extends JpaRepository<School, Integer> {

    /**
     * Checks if a School is already exists.
     *
     * @param name the name of the school
     * @return if school exists
     */
    boolean existsByName(String name);

    /**
     * Gets a school by its name.
     * @param name The name of the school
     * @return A new school object
     */
    School getByName(String name);
}
