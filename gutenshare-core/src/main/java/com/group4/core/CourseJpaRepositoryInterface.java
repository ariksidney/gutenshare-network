package com.group4.core;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseJpaRepositoryInterface extends JpaRepository<Course, Integer> {

    boolean existsByName(String name);

    Course getByName(String name);
}
