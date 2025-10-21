package com.example.libreria_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.libreria_app.repository.ComunaRepository;
import com.example.libreria_app.model.Comuna;

import java.util.List;
import java.util.Optional;

@Service
public class ComunaService {
    @Autowired
    private ComunaRepository comunaRepository;

    public List<Comuna> findAll() {
        return comunaRepository.findAll();
    }

    public Optional<Comuna> findById(Long id) {
        return comunaRepository.findById(id);
    }

    public Comuna save(Comuna comuna) {
        return comunaRepository.save(comuna);
    }

    public void deleteById(Long id) {
        comunaRepository.deleteById(id);
    }
}
