package com.group4.core;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentJpaRepositoryInterface extends JpaRepository<Department, Integer> {

    boolean existsByName(String name);

    Department getByName(String name);
}
