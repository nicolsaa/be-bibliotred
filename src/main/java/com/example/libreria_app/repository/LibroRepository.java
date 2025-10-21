package com.example.libreria_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.libreria_app.model.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, String> {

}
