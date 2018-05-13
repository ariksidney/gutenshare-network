package com.group4.core;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface to interact with Department database table.
 *
 * @author Arik Sidney Guggenheim
 * @version 1.0
 */
public interface DepartmentJpaRepositoryInterface extends JpaRepository<Department, Integer> {

    /**
     * Checks if a department with the given name exists.
     *
     * @param name Name of department to check
     * @return if department exists
     */
    boolean existsByName(String name);

    /**
     * Gets a department by the name.
     *
     * @param name Name of department to get
     * @return Department object
     */
    Department getByName(String name);
}
