package com.example.libreria_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.libreria_app.dto.LoginDto;
import com.example.libreria_app.dto.UsuarioDTO;
import com.example.libreria_app.model.Usuario;
import com.example.libreria_app.repository.UsuarioRepository;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDTO login(LoginDto loginDto) {
        if (loginDto == null || loginDto.getCorreo() == null || loginDto.getCorreo().isEmpty()
                || loginDto.getContrasena() == null || loginDto.getContrasena().isEmpty()) {
            throw new IllegalArgumentException("Correo y/o contraseña no pueden estar vacíos");
        }

        Optional<Usuario> opt = usuarioRepository.findByCorreoAndContrasena(loginDto.getCorreo(), loginDto.getContrasena());
        if (opt.isPresent()) {
            Usuario u = opt.get();
            UsuarioDTO dto = new UsuarioDTO();
            dto.setId(u.getId());
            dto.setNombre(u.getNombre());
            dto.setApellido(u.getApellido());
            dto.setCorreo(u.getCorreo());
            dto.setContrasena(u.getContrasena());
            return dto;
        } else {
            throw new RuntimeException("problema al iniciar sesión asegúrese de ingresar correo y/o contraseña correctamente");
        }
    }
}
