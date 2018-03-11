package com.group4.gutenshareweb.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group4.core.Document;
import com.group4.core.User;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    @PostMapping(value = "/create")
    public @ResponseBody String createDocument(@RequestBody String jsonString) {

        System.out.println(jsonString);

        try {
            Document doc = parseToDocument(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    private Document parseToDocument(String json) throws IOException {
        ObjectMapper om = new ObjectMapper();
        Document doc = om.readValue(json, Document.class);
        return doc;
    }
}
