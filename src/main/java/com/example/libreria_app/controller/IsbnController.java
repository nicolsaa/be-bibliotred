package com.example.libreria_app.controller;

import com.example.libreria_app.client.response.GenericKeyDto;
import com.example.libreria_app.client.response.OpenLibratyEditionDto;
import com.example.libreria_app.service.IsbnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/openlibrary")
public class IsbnController {

    @Autowired
    private IsbnService isbnService;

    @GetMapping(value = "/getBooks/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<OpenLibratyEditionDto> getBooksByIsbn(@PathVariable String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<OpenLibratyEditionDto> edition = isbnService.getEditionByIsbn(isbn);

        return edition.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.unprocessableEntity().build());
    }

    @GetMapping(value = "/getAuthors/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GenericKeyDto> getAuthors(@PathVariable String key) {
        if (key == null || key.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<GenericKeyDto> edition = isbnService.getAuthorsByKey(key);

        return edition.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.unprocessableEntity().build());

    }
}
