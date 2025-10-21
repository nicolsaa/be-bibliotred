package com.example.libreria_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.libreria_app.model.Direccion;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long>{

}
