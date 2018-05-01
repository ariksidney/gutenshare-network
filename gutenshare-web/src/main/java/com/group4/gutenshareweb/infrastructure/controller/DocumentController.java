package com.group4.gutenshareweb.infrastructure.controller;

import com.group4.api.CreateDocumentDto;
import com.group4.api.DeliverDocumentDto;
import com.group4.api.DocumentService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
            @RequestParam("school") Optional<String> school,
            @RequestParam("department") Optional<String> department,
            @RequestParam("course") Optional<String> course,
            @RequestParam("tags") Optional<List<String>> tags,
            @RequestParam("description") Optional<String> description,
            @RequestParam("document") MultipartFile document
    ) throws IOException {

        String fileType = FilenameUtils.getExtension(document.getOriginalFilename());
        CreateDocumentDto documentDto = new CreateDocumentDto(
                "",
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

    @GetMapping(value = "/{documentId}")
    public ResponseEntity<DeliverDocumentDto> getDocumentById(@PathVariable String documentId) {
        Optional<DeliverDocumentDto> deliverableDocument = documentService.getDocumentById(documentId);
        return deliverableDocument.map(document -> new ResponseEntity<>(document, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
