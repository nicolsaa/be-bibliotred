package com.example.libreria_app.service;

import com.example.libreria_app.client.openlibrary.CoverClient;
import com.example.libreria_app.client.openlibrary.OpenLibraryClient;
import com.example.libreria_app.client.openlibrary.response.GenericKeyDto;
import com.example.libreria_app.client.openlibrary.response.OpenLibratyEditionDto;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OpenLibraryService {

    @Autowired
    private OpenLibraryClient openLibraryClient;

    @Autowired
    private CoverClient coverClient;

    public Optional<OpenLibratyEditionDto> getEditionByIsbn(String isbn) {
        try {
            ResponseEntity<OpenLibratyEditionDto> response =  openLibraryClient.getEditionByIsbn(isbn);
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
        ResponseEntity<GenericKeyDto> response =  openLibraryClient.getAuthorsByKey(key);

        if (response.getStatusCode().is2xxSuccessful()) {
            return Optional.ofNullable(response.getBody());
        } else {
            return Optional.empty();
        }
    }

    public Optional<byte[]> getCoverByIdAndSize(String id, String size) {
        ResponseEntity<byte[]> response = coverClient.getCoverByIdAndSize(id, size);

        if (response.getStatusCode().is2xxSuccessful()) {
            return Optional.ofNullable(response.getBody());
        } else {
            return Optional.empty();
        }
    }

    public Optional<byte[]> getIsbnCoverByCodeAndSize(String code, String size) {
        ResponseEntity<byte[]> response = coverClient.getIsbnCoverByCodeAndSize(code, size);

        if (response.getStatusCode().is2xxSuccessful()) {
            return Optional.ofNullable(response.getBody());
        } else {
            return Optional.empty();
        }
    }

}
