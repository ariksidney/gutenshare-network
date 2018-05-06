package com.group4.gutenshareweb.infrastructure.controller;

import com.group4.api.CommentAndRateService;
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
    private final CommentAndRateService commentAndRateService;

    public DocumentController(DocumentService documentService, CommentAndRateService commentAndRateService) {
        this.documentService = documentService;
        this.commentAndRateService = commentAndRateService;
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
            @RequestParam("document") MultipartFile document,
            @RequestParam("user") String username
    ) throws IOException {
        String fileType = FilenameUtils.getExtension(document.getOriginalFilename());
        documentService.storeNewDocument(title,
                documentType,
                school,
                department,
                course,
                tags,
                description,
                document.getInputStream(),
                username,
                fileType);
        return HttpStatus.CREATED;
    }

    @GetMapping(value = "/{documentId}")
    public ResponseEntity<DeliverDocumentDto> getDocumentById(@PathVariable String documentId) {
        Optional<DeliverDocumentDto> deliverableDocument = documentService.getDocumentById(documentId);
        return deliverableDocument.map(document -> new ResponseEntity<>(document, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/comment")
    public ResponseEntity<DeliverDocumentDto> addComment(@RequestParam("documentid") String documentId,
                                                         @RequestParam("comment") String comment,
                                                         @RequestParam("user") String username) {
        Optional<DeliverDocumentDto> deliverableDocument = commentAndRateService.addComment(documentId, comment,
                username);
        return deliverableDocument.map(document -> new ResponseEntity<>(document, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/rating")
    public ResponseEntity<DeliverDocumentDto> addReview(@RequestParam("documentid") String documentId,
                                                        @RequestParam("rating") Integer rating,
                                                        @RequestParam("user") String username) {
        Optional<DeliverDocumentDto> deliverableDocument = commentAndRateService.addRating(documentId, rating,
                username);
        return deliverableDocument.map(document -> new ResponseEntity<>(document, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
