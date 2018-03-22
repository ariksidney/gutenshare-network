package com.group4.gutenshareweb.infrastructure.controller;

import com.group4.api.DocumentDto;
import com.group4.api.DocumentService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public HttpStatus storeDocument(
            @RequestParam("title") String title,
            @RequestParam("documenttype") String documentType,
            @RequestParam("school") String school,
            @RequestParam("department") String department,
            @RequestParam("course") String course,
            @RequestParam("tags") Optional<List<String>> tags,
            @RequestParam("description") String description,
            @RequestParam("document") MultipartFile document
    ) throws IOException {

        String fileType = FilenameUtils.getExtension(document.getOriginalFilename());
        DocumentDto documentDto = new DocumentDto(
                title,
                documentType,
                school,
                department,
                course,
                fileType,
                tags,
                description,
                document.getInputStream()
        );
        documentService.storeNewDocument(documentDto);
        return HttpStatus.CREATED;
    }
}
