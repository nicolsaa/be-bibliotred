package com.example.libreria_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.libreria_app.model.Comuna;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long> {

}
