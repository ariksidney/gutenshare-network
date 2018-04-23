package com.group4.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentJpaRepositoryInterface extends JpaRepository<Document, Integer>{

    Document getById(String documentId);

    @Query("select c from Document c where c.title like %:q% or " +
            "c.description like %:q% or " +
            "c.documentType like %:q% or " +
            "c.fileType = :q")
    List<Document> findAllBySearchQuery(@Param("q") String query);
}
