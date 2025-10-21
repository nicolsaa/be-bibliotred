package com.example.libreria_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.libreria_app.model.Resena_libro;

@Repository
public interface Resena_libroRepository extends JpaRepository<Resena_libro,Long> {

}
