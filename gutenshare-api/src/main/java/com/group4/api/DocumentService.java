package com.group4.api;

import com.group4.core.Document;
import com.group4.core.DocumentJpaRepositoryInterface;
import com.group4.core.DocumentStoreRepositoryInterface;
import com.group4.core.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class DocumentService {

    private final DocumentStoreRepositoryInterface documentStoreRepositoryInterface;
    private final DocumentJpaRepositoryInterface documentJpaRepositoryInterface;

    @Autowired
    public DocumentService(DocumentStoreRepositoryInterface documentStoreRepositoryInterface,
                           DocumentJpaRepositoryInterface documentJpaRepositoryInterface) {
        this.documentStoreRepositoryInterface = documentStoreRepositoryInterface;
        this.documentJpaRepositoryInterface = documentJpaRepositoryInterface;
    }

    public void storeNewDocument(DocumentDto documentDto) {
        List<Tag> tags = new ArrayList<>();
        if (documentDto.getTags().isPresent()) {
            documentDto.getTags().get().forEach(tag -> tags.add(new Tag.TagBuilder().setName(tag).build()));
        }
        Document document = new Document.DocumentBuilder()
                .setTitle(documentDto.getTitle())
                .setDocumentType(documentDto.getDocumentType())
                .setFileType(documentDto.getFileType())
                .setInputStream(documentDto.getDocumentStream())
                .setTags(tags)
                .setDescription(documentDto.getDescription())
                .build();
        document.storeFile(this.documentStoreRepositoryInterface);
        this.documentJpaRepositoryInterface.save(document);
    }
}
