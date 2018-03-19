package com.group4.gutenshareweb.infrastructure.controller;

import com.group4.api.DocumentDto;
import com.group4.api.DocumentService;
import com.group4.core.Document;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public HttpStatus storeDocument(@RequestParam("title") String title, @RequestParam("document") MultipartFile
            document) throws IOException {
        String type = FilenameUtils.getExtension(document.getOriginalFilename());
        DocumentDto documentDto = new DocumentDto(title, type, document.getInputStream());
        documentService.storeNewDocument(title, documentDto);
        return HttpStatus.CREATED;
    }
}
