package com.group4.api;

import com.group4.core.Document;
import com.group4.core.DocumentJpaRepository;
import com.group4.core.DocumentStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class DocumentService {

    private final DocumentStoreRepository documentStoreRepository;
    private final DocumentJpaRepository documentJpaRepository;

    @Autowired
    public DocumentService(DocumentStoreRepository documentStoreRepository, DocumentJpaRepository documentJpaRepository) {
        this.documentStoreRepository = documentStoreRepository;
        this.documentJpaRepository = documentJpaRepository;
    }

    public void storeNewDocument(String title, Document document) {
        document.storeFile(title, this.documentStoreRepository);
        this.documentJpaRepository.save(document);
    }
}
