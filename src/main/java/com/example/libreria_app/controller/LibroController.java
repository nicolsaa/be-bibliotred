package com.example.libreria_app.controller;

import com.example.libreria_app.service.LibroService;
import com.example.libreria_app.model.Autor;
import com.example.libreria_app.model.Libro;
import com.example.libreria_app.dto.ISBNRequest;
import com.example.libreria_app.dto.LibroFromIsbnRequest;
import com.example.libreria_app.dto.LibroScanResponse;

import java.util.List;
import java.util.NoSuchElementException;
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

    @PostMapping("/scan-barcode")
    public LibroScanResponse scanBarcode(@RequestBody ISBNRequest request, HttpSession session) {
        String isbn = request.getIsbn();
        Libro libro = libroService.scanBarcode(isbn, session);

        LibroScanResponse response = new LibroScanResponse();
        response.setIsbn(isbn);
        response.setTitulo(libro.getTitulo());

        List<String> autores = libro.getAutores().stream()
                .map(Autor::getNombre)
                .collect(Collectors.toList());
        response.setAutores(autores);

        List<String> generos = new java.util.ArrayList<>();
        response.setGeneros(generos);

        return response;
    }

    @PostMapping("/add-libro")
    public ResponseEntity<Libro> scanBarcode(@RequestBody LibroFromIsbnRequest request, HttpSession session) {
        Libro libro = libroService.scanBarcode(request.getIsbn(), session);
        if (libro == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(libro);
    }

    @PostMapping("/from-isbn")
    public ResponseEntity<Libro> createFromIsbn(@RequestBody LibroFromIsbnRequest request) {
        Libro libro = libroService.createOrGetBookFromIsbn(request.getIsbn());
        if (libro == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(libro);
    }

    @GetMapping
    public List<Libro> listAllLibros() {
        return libroService.listAllLibros();
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
}
