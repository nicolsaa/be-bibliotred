package com.example.libreria_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.example.libreria_app.model.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long>{

    Optional<Autor> findByNombreIgnoreCase(String nombre);
}
