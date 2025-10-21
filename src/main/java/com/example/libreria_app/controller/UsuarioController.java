package com.example.libreria_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.libreria_app.service.UsuarioService;

import com.example.libreria_app.dto.UsuarioDTO;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Nuevo: registrar usuario
    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody UsuarioDTO dto) {
        if (dto == null) {
            return ResponseEntity.badRequest().body("Solicitud invalida");
        }
        return ResponseEntity.ok(usuarioService.registrarUsuario(dto));
    }

    // Nuevo: listar todos los usuarios (DTO)
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodosLosUsuariosDTO());
    }

    // Nuevo: eliminar usuario por correo
    @DeleteMapping("/{correo}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable String correo) {
        return usuarioService.eliminarUsuarioPorCorreo(correo);
    }


}
