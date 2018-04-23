package com.group4.core;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolJpaRepositoryInterface extends JpaRepository<School, Integer> {

    boolean existsByName(String name);

    School getByName(String name);
}
