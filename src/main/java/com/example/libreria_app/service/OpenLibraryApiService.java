package com.example.libreria_app.service;

import java.util.ArrayList;
import java.util.List;

import com.example.libreria_app.dto.BookData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenLibraryApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenLibraryApiService() {
        this.restTemplate = new RestTemplate();
    }

    public BookData fetchBookData(String isbn) {
        try {
            String uri = "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data";
            String response = restTemplate.getForObject(uri, String.class);

            if (response == null || response.isEmpty()) {
                return new BookData();
            }

            JsonNode root = objectMapper.readTree(response);
            JsonNode bookNode = root.path("ISBN:" + isbn);

            BookData bookData = new BookData();

            if (bookNode.has("title")) {
                bookData.setTitle(bookNode.get("title").asText());
            }

            List<String> authorNames = new ArrayList<>();
            if (bookNode.has("authors")) {
                for (JsonNode authorNode : bookNode.get("authors")) {
                    if (authorNode.has("name")) {
                        authorNames.add(authorNode.get("name").asText());
                    }
                }
            }
            bookData.setAuthorNames(authorNames);

            List<String> genreNames = new ArrayList<>();
            if (bookNode.has("subjects")) {
                for (JsonNode subjectNode : bookNode.get("subjects")) {
                    if (subjectNode.has("name")) {
                        genreNames.add(subjectNode.get("name").asText());
                    } else if (subjectNode.isTextual()) {
                        genreNames.add(subjectNode.asText());
                    }
                }
            }
            bookData.setGenreNames(genreNames);

            return bookData;
        } catch (Exception e) {
            // En caso de error, devolver objeto vacío para que la capa superior maneje el fallback
            return new BookData();
        }
    }
}
