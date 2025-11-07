package com.example.libreria_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.libreria_app.model.Libro;
import com.example.libreria_app.model.Usuario;

@Repository
public interface LibroRepository extends JpaRepository<Libro, String> {

    List<Libro> findByPropietario(Usuario propietario);

}
