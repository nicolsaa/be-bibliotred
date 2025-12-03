package com.example.libreria_app.service;

import com.example.libreria_app.client.IsbnClient;
import com.example.libreria_app.client.response.GenericKeyDto;
import com.example.libreria_app.client.response.OpenLibratyEditionDto;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IsbnService {

    @Autowired
    private IsbnClient isbnClient;

    public Optional<OpenLibratyEditionDto> getEditionByIsbn(String isbn) {
        String cleanIsbn = isbn != null ? isbn.replace("\"", "").trim() : isbn;
        try {
            ResponseEntity<OpenLibratyEditionDto> response =  isbnClient.getEditionByIsbn(cleanIsbn);
            if (response.getStatusCode().is2xxSuccessful()) {
                return Optional.ofNullable(response.getBody());
            } else {
                return Optional.empty();
            }
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }

    public Optional<GenericKeyDto> getAuthorsByKey(String key) {
        ResponseEntity<GenericKeyDto> response =  isbnClient.getAuthorsByKey(key);

        if (response.getStatusCode().is2xxSuccessful()) {
            return Optional.ofNullable(response.getBody());
        } else {
            return Optional.empty();
        }
    }

}
