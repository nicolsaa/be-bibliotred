package com.example.libreria_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.libreria_app.model.Resena_libro;
import com.example.libreria_app.service.Resena_libroService;

@RestController
@RequestMapping("/resenas")
public class Resena_libroController {
    @Autowired
    private Resena_libroService resena_libroService;

    @GetMapping
    public List<Resena_libro> getAllResenas() {
        return resena_libroService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resena_libro> getResena(@PathVariable Long id) {
        return resena_libroService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Resena_libro> createResena(@RequestBody Resena_libro resena) {
        Resena_libro saved = resena_libroService.saveResena(resena);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resena_libro> updateResena(@PathVariable Long id, @RequestBody Resena_libro updated) {
        return resena_libroService.updateResena(id, updated)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResena(@PathVariable Long id) {
        boolean deleted = resena_libroService.deleteResena(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
