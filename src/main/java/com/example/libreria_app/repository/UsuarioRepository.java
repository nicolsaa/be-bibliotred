package com.example.libreria_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.libreria_app.model.Usuario;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
    Usuario findByCorreo(String correo);
    Optional<Usuario> findByCorreoAndContrasena(String correo, String contrasena);

    void deleteByCorreo(String correo);
}
