package com.group4.gutenshareweb.infrastructure.repository;

import com.group4.core.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier(value = "documentRepository")
public interface DocumentRepository extends CrudRepository<Document, Long> {

}
