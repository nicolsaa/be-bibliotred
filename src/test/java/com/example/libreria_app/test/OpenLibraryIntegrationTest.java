package com.example.libreria_app.test;

import com.example.libreria_app.client.IsbnClient;
import com.example.libreria_app.client.response.GenericKeyDto;
import com.example.libreria_app.client.response.OpenLibratyEditionDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.libreria_app.LibreriaAppApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = LibreriaAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("intTest")
public class OpenLibraryIntegrationTest {

    @Autowired
    private IsbnClient isbnClient;

    @Test
    void shouldGetBookInfoByIsbnTenTest() {
        ResponseEntity<OpenLibratyEditionDto> response = this.isbnClient.getEditionByIsbn("0140621350");

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void shouldGetAuthorsByKeyTest() {
        ResponseEntity<GenericKeyDto> response = this.isbnClient.getAuthorsByKey("OL7357141M");

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
    }
}
