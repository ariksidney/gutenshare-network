package com.group4.api;

import com.group4.core.Document;
import com.group4.core.DocumentJpaRepositoryInterface;
import com.group4.core.DocumentStoreRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class DocumentService {

    private final DocumentStoreRepositoryInterface documentStoreRepositoryInterface;
    private final DocumentJpaRepositoryInterface documentJpaRepositoryInterface;

    @Autowired
    public DocumentService(DocumentStoreRepositoryInterface documentStoreRepositoryInterface, DocumentJpaRepositoryInterface documentJpaRepositoryInterface) {
        this.documentStoreRepositoryInterface = documentStoreRepositoryInterface;
        this.documentJpaRepositoryInterface = documentJpaRepositoryInterface;
    }

    public void storeNewDocument(String title, DocumentDto documentDto) {
        Document document = new Document.DocumentBuilder()
                .setTitle(documentDto.getTitle())
                .setFiletype(documentDto.getFiletype())
                .setInputStream(documentDto.getDocumentStream())
                .build();
        document.storeFile(title, this.documentStoreRepositoryInterface);
        this.documentJpaRepositoryInterface.save(document);
    }
}
