package com.example.libreria_app.client.openlibrary;

import com.example.libreria_app.client.openlibrary.response.GenericKeyDto;
import com.example.libreria_app.client.openlibrary.response.OpenLibratyEditionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${endpoints.openlibrary.baseurl}", name = "isbnClient" )
public interface OpenLibraryClient {

    @GetMapping("/isbn/{isbn}.json")
    ResponseEntity<OpenLibratyEditionDto> getEditionByIsbn(@PathVariable("isbn") String isbn);

    @GetMapping("/authors/{key}.json")
    ResponseEntity<GenericKeyDto> getAuthorsByKey(@PathVariable("key") String key);
}
