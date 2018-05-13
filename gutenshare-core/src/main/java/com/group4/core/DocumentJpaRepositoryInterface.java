package com.group4.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface to interact with Document database table.
 *
 * @author Arik Sidney Guggenheim
 * @version 1.0
 */
public interface DocumentJpaRepositoryInterface extends JpaRepository<Document, Integer> {

    /**
     * Gets a Document by a document id.
     *
     * @param documentId Id of document to get
     * @return Document object
     */
    Document getById(String documentId);

    /**
     * Finds all documents based on a given search query
     *
     * @param query The search query
     * @return List of documents found by the search
     */
    @Query("select c from Document c where c.title like %:q% or " +
            "c.description like %:q% or " +
            "c.documentType like %:q% or " +
            "c.fileType = :q")
    List<Document> findAllBySearchQuery(@Param("q") String query);

    /**
     *Finds all documents beloning to school, deparment, course or any combination of it (like just school and course)
     *
     * @param school The name of a school
     * @param department The name of a department
     * @param course The name of a course
     * @return List of documents found by the query
     */
    @Query("select c from Document c join c.department d join c.school s join c.course t where " +
            "((:department is null) or (:department is not null and d.name = :department)) and " +
            "((:school is null) or (:school is not null and s.name = :school)) and " +
            "((:course is null) or (:course is not null and t.name = :course))")
    List<Document> findBySchoolAndDeptAndCourse(@Param("school") String school, @Param("department") String department, @Param("course")
            String course);



}
