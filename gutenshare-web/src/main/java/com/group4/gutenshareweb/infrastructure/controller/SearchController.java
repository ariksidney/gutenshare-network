package com.group4.gutenshareweb.infrastructure.controller;

import com.group4.api.DocumentDto;
import com.group4.api.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final DocumentService documentService;

    public SearchController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public @ResponseBody
    ResponseEntity<List<DocumentDto>> searchDocuments(@RequestParam("query") String query) {
        Optional<List<DocumentDto>> results = this.documentService.getDocumentsFromSearchQuery(query);
        return results.map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
