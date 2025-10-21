package com.example.libreria_app.controller;

import com.example.libreria_app.model.Comuna;
import com.example.libreria_app.service.ComunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comunas")
public class ComunaController {
    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public List<Comuna> getAllComunas() {
        return comunaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comuna> getComunaById(@PathVariable Long id) {
        return comunaService.findById(id)
                .map(comuna -> ResponseEntity.ok(comuna))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Comuna> createComuna(@RequestBody Comuna comuna) {
        Comuna saved = comunaService.save(comuna);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comuna> updateComuna(@PathVariable Long id, @RequestBody Comuna comuna) {
        Optional<Comuna> existing = comunaService.findById(id);
        if (existing.isPresent()) {
            Comuna toUpdate = existing.get();
            // Copiar campos relevantes del DTO/entidad entrante
            toUpdate.setNombre(comuna.getNombre());
            Comuna updated = comunaService.save(toUpdate);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComuna(@PathVariable Long id) {
        Optional<Comuna> existing = comunaService.findById(id);
        if (existing.isPresent()) {
            comunaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
