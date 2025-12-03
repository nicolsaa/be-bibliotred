package com.example.libreria_app.test;

import com.example.libreria_app.client.openlibrary.CoverClient;
import com.example.libreria_app.client.openlibrary.OpenLibraryClient;
import com.example.libreria_app.client.openlibrary.response.GenericKeyDto;
import com.example.libreria_app.client.openlibrary.response.OpenLibratyEditionDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.libreria_app.LibreriaAppApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = LibreriaAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("intTest")
public class OpenLibraryIntegrationTest {

    @Autowired
    private OpenLibraryClient openLibraryClient;

    @Autowired
    private CoverClient coverClient;

    @Test
    void shouldGetBookInfoByIsbnTenTest() {
        ResponseEntity<OpenLibratyEditionDto> response = this.openLibraryClient.getEditionByIsbn("0140621350");

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void shouldGetAuthorsByKeyTest() {
        ResponseEntity<GenericKeyDto> response = this.openLibraryClient.getAuthorsByKey("OL7357141M");

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void shouldGetCoverByIdAndSizeTest() {
        ResponseEntity<byte[]> response = this.coverClient.getCoverByIdAndSize("OL1203378M", "M");

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void shouldGetIsbnCoverByCodeAndSize() {
        ResponseEntity<byte[]> response = this.coverClient.getIsbnCoverByCodeAndSize("9788495100667", "M");

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
    }
}
