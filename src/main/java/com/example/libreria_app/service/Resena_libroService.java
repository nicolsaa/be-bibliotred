package com.example.libreria_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.libreria_app.model.Resena_libro;
import com.example.libreria_app.repository.Resena_libroRepository;

@Service
public class Resena_libroService {
    @Autowired
    private Resena_libroRepository resena_libroRepository;

    public List<Resena_libro> findAll() {
        return resena_libroRepository.findAll();
    }

    public Optional<Resena_libro> findById(Long id) {
        return resena_libroRepository.findById(id);
    }

    public Resena_libro saveResena(Resena_libro resena) {
        return resena_libroRepository.save(resena);
    }

    public Optional<Resena_libro> updateResena(Long id, Resena_libro updated) {
        return resena_libroRepository.findById(id).map(existing -> {
            existing.setTitulo_resena(updated.getTitulo_resena());
            existing.setComentario(updated.getComentario());
            existing.setCalificacion(updated.getCalificacion());
            existing.setFecha(updated.getFecha());
            existing.setLibro(updated.getLibro());
            existing.setUsuario(updated.getUsuario());
            return resena_libroRepository.save(existing);
        });
    }

    public boolean deleteResena(Long id) {
        if (resena_libroRepository.existsById(id)) {
            resena_libroRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
