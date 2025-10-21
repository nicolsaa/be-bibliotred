package com.example.libreria_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.libreria_app.repository.DireccionRepository;
import com.example.libreria_app.model.Direccion;
import com.example.libreria_app.model.Comuna;
import com.example.libreria_app.repository.ComunaRepository;
import com.example.libreria_app.dto.DireccionUpdateRequest;
import java.util.List;
import java.util.Optional;

@Service
public class DireccionService {
    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    public Direccion saveDireccion(Direccion direccion) {
        return direccionRepository.save(direccion);
    }

    public List<Direccion> getAllDirecciones() {
        return direccionRepository.findAll();
    }

    public Optional<Direccion> getDireccionById(Long id) {
        return direccionRepository.findById(id);
    }

    public void deleteDireccion(Long id) {
        direccionRepository.deleteById(id);
    }

    public Optional<Direccion> updateDireccion(Long id, DireccionUpdateRequest update) {
        Optional<Direccion> existing = direccionRepository.findById(id);
        if (!existing.isPresent()) {
            return Optional.empty();
        }
        Direccion d = existing.get();

        if (update.getCalle() != null) {
            d.setCalle(update.getCalle());
        }
        if (update.getNumero() != null) {
            d.setNumero(update.getNumero());
        }
        if (update.getComunaId() != null) {
            Optional<Comuna> comunaOpt = comunaRepository.findById(update.getComunaId());
            Comuna comuna = comunaOpt.orElse(null);
            d.setComuna(comuna);
        }

        direccionRepository.save(d);
        return Optional.of(d);
    }
}
