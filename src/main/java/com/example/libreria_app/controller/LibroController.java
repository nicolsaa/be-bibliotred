package com.example.libreria_app.controller;

import com.example.libreria_app.service.LibroService;

import com.example.libreria_app.model.Libro;
import com.example.libreria_app.dto.ISBNRequest;
import com.example.libreria_app.dto.BookData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/libros")
public class LibroController {
    @Autowired
    private LibroService libroService;

@PostMapping("/add-libro")
public ResponseEntity<Libro> addLibro(@RequestBody ISBNRequest request, HttpSession session) {
        Libro libro = libroService.scanBarcode(request.getIsbn(),request.getCorreoPropietario(), session);
        if (libro == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(libro);
    }

    @PostMapping("/from-isbn")
    public ResponseEntity<Libro> createFromIsbn(@RequestBody ISBNRequest request) {
            Libro libro = libroService.createOrGetBookFromIsbn(request.getIsbn());
            if (libro == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(libro);
        }

    @GetMapping
    public List<BookData> listAllLibros() {
    return libroService.listAllLibros().stream()
            .map(com.example.libreria_app.mapper.LibroMapper::toBookData)
            .collect(Collectors.toList());
    }

    @DeleteMapping("/{codigoBarra}")
    public ResponseEntity<Void> deleteLibro(@PathVariable("codigoBarra") String codigoBarra) {
        try {
            libroService.deleteLibroByCodigoBarra(codigoBarra);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String, Object>> getBookByEmail(@PathVariable(value = "email") String userEmail) {
        if (Objects.isNull(userEmail)) {
            return ResponseEntity.badRequest().body(Map.of("error", "userEmail not present"));
        }

        return ResponseEntity.ok(Map.of("libros", this.libroService.getBookByEmail(userEmail)));
    }
}
