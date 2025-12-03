package com.example.libreria_app.client.openlibrary;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${endpoints.covers.openlibrary.baseurl}", name = "coverClient")
public interface CoverClient {

    @GetMapping("/id/{id}-{size}.jpg")
    ResponseEntity<byte[]> getCoverByIdAndSize(@PathVariable("id") String id, @PathVariable("size") String size);

    @GetMapping("/isbn/{code}-{size}.jpg")
    ResponseEntity<byte[]> getIsbnCoverByCodeAndSize(@PathVariable("code") String id, @PathVariable("size") String size);

}
