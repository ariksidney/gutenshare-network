package com.group4.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentJpaRepositoryInterface extends JpaRepository<Document, Integer> {

    Document getById(String documentId);

    @Query("select c from Document c where c.title like %:q% or " +
            "c.description like %:q% or " +
            "c.documentType like %:q% or " +
            "c.fileType = :q")
    List<Document> findAllBySearchQuery(@Param("q") String query);

    @Query("select c from Document c join c.department d join c.school s join c.course t where " +
            "((:department is null) or (:department is not null and d.name = :department)) and " +
            "((:school is null) or (:school is not null and s.name = :school)) and " +
            "((:course is null) or (:course is not null and t.name = :course))")
    List<Document> findBySchoolAndDeptAndCourse(@Param("school") String school, @Param("department") String department, @Param("course")
            String course);



}
