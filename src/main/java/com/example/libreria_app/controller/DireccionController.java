package com.example.libreria_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.libreria_app.service.DireccionService;
import com.example.libreria_app.model.Direccion;
import com.example.libreria_app.model.Comuna;
import com.example.libreria_app.dto.DireccionCreateRequest;
import com.example.libreria_app.dto.DireccionUpdateRequest;
import com.example.libreria_app.repository.ComunaRepository;
import io.swagger.v3.oas.annotations.Hidden;

import java.util.List;

@Hidden
@RestController
@RequestMapping("/direcciones")
public class DireccionController {
    @Autowired
    private DireccionService direccionService;

    @Autowired
    private ComunaRepository comunaRepository;

    @Hidden
    @GetMapping
    public List<Direccion> getAllDirecciones() {
        return direccionService.getAllDirecciones();
    }

    @Hidden
    @GetMapping("/{id}")
    public ResponseEntity<Direccion> getDireccionById(@PathVariable Long id) {
        return direccionService.getDireccionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public Direccion createDireccion(@RequestBody DireccionCreateRequest request) {
        Direccion direccion = new Direccion();
        direccion.setCalle(request.getCalle());
        direccion.setNumero(request.getNumero());

        if (request.getComunaId() != null) {
            Comuna comuna = comunaRepository.findById(request.getComunaId()).orElse(null);
            direccion.setComuna(comuna);
        }

        return direccionService.saveDireccion(direccion);
    }

    @Hidden
    @PutMapping("/{id}")
    public ResponseEntity<Direccion> updateDireccion(@PathVariable Long id, @RequestBody DireccionUpdateRequest update) {
        return direccionService.updateDireccion(id, update)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Hidden
    @DeleteMapping("/{id}")
    public void deleteDireccion(@PathVariable Long id) {
        direccionService.deleteDireccion(id);
    }
}
