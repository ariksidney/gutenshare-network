package com.group4.gutenshareweb.infrastructure.controller;

import com.group4.api.CategoryDto;
import com.group4.api.CategoryService;
import com.group4.api.DocumentDto;
import com.group4.api.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SearchController {

    private final DocumentService documentService;
    private final CategoryService categoryService;

    public SearchController(DocumentService documentService, CategoryService categoryService) {
        this.documentService = documentService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/search")
    public @ResponseBody
    ResponseEntity<List<DocumentDto>> searchDocuments(@RequestParam("query") String query) {
        Optional<List<DocumentDto>> results = this.documentService.getDocumentsFromSearchQuery(query);
        return results.map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping(value = "/browse")
    public @ResponseBody
    ResponseEntity<List<DocumentDto>> browseDocument(@RequestParam("school") Optional<String> school, @RequestParam
            ("departement") Optional<String> dep, @RequestParam("course") Optional<String> course) {
        Optional<List<DocumentDto>> results = this.documentService.getDocumentsFromBrowse(school.orElse(null), dep
                .orElse(null), course.orElse(null));
        return results.map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping(value = "/categories")
    public @ResponseBody ResponseEntity<CategoryDto> getCategories() {
        CategoryDto categoryDto = categoryService.getAllCategories();
        if (categoryDto.getCourses().isEmpty() || categoryDto.getDepartments().isEmpty() || categoryDto.getSchools()
                .isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        }
    }
}
