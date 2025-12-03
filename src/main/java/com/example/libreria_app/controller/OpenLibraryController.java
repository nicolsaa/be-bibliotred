package com.example.libreria_app.controller;

import com.example.libreria_app.client.openlibrary.response.GenericKeyDto;
import com.example.libreria_app.client.openlibrary.response.OpenLibratyEditionDto;
import com.example.libreria_app.service.OpenLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/openlibrary")
public class OpenLibraryController {

    @Autowired
    private OpenLibraryService openLibraryService;

    @GetMapping(value = "/getBooks/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<OpenLibratyEditionDto> getBooksByIsbn(@PathVariable String isbn) {
        if (Objects.isNull(isbn)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<OpenLibratyEditionDto> edition = openLibraryService.getEditionByIsbn(isbn);

        return edition.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.unprocessableEntity().build());
    }

    @GetMapping(value = "/getAuthors/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GenericKeyDto> getAuthors(@PathVariable String key) {
        if (Objects.isNull(key)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<GenericKeyDto> edition = openLibraryService.getAuthorsByKey(key);

        return edition.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.unprocessableEntity().build());

    }

    @GetMapping(value = "/getCover/{id}-{size}", produces = MediaType.IMAGE_JPEG_VALUE)
    ResponseEntity<byte[]> getCover(@PathVariable("id") String id, @PathVariable("size") String size) {
        if (Objects.isNull(id) || Objects.isNull(size)) {
            return ResponseEntity.badRequest().build();
        }

        return this.openLibraryService.getCoverByIdAndSize(id, size)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/getIsbnCover/{code}-{size}", produces = MediaType.IMAGE_JPEG_VALUE)
    ResponseEntity<byte[]> getIsbnCover(@PathVariable("code") String code, @PathVariable("size") String size) {
        if (Objects.isNull(code) || Objects.isNull(size)) {
            return ResponseEntity.badRequest().build();
        }

        return this.openLibraryService.getIsbnCoverByCodeAndSize(code, size).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
