package com.example.libreria_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.libreria_app.dto.LoginDto;
import com.example.libreria_app.dto.UsuarioDTO;
import com.example.libreria_app.service.LoginService;

@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            UsuarioDTO dto = loginService.login(loginDto);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            // Campos vacíos o inválidos
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            // Credenciales incorrectas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
