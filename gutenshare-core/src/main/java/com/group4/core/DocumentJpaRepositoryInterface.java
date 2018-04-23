package com.group4.core;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentJpaRepositoryInterface extends JpaRepository<Document, Integer>{

    Document getById(String documentId);
}
