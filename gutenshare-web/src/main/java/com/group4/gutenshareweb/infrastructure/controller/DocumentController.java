package com.group4.gutenshareweb.infrastructure.controller;

import com.group4.api.DocumentService;
import com.group4.core.Document;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public HttpStatus storeDocument(@RequestParam("title") String title, @RequestParam("document") MultipartFile document) throws IOException {
        String type = document.getContentType();
        // Shouldn't create a document directly but should create a DTO instead
        Document documentToCreate = new Document.DocumentBuilder()
                .setFiletype(type.substring(type.length() - 3, type.length()))
                .setTitle(title)
                .setInputStream(document.getInputStream())
                .build();
        documentService.storeNewDocument(title, documentToCreate);
        return HttpStatus.CREATED;
    }
}
