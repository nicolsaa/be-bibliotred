package com.example.libreria_app.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.libreria_app.dto.UsuarioDTO;
import com.example.libreria_app.model.Usuario;
import com.example.libreria_app.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public String registrarUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByCorreo(usuarioDTO.getCorreo())== null){
            Usuario usuario = new Usuario();

            usuario.setCorreo(usuarioDTO.getCorreo());
            usuario.setApellido(usuarioDTO.getApellido());
            usuario.setNombre(usuarioDTO.getNombre());
            usuario.setContrasena(usuarioDTO.getContrasena());

            usuarioRepository.save(usuario);
            return "Usuario registrado exitosamente.";
        } else {
            return "El usuario con correo " + usuarioDTO.getCorreo() + " ya existe";
        }
    }


    public List<UsuarioDTO> listarTodosLosUsuariosDTO() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setId(usuario.getId());
            dto.setNombre(usuario.getNombre());
            dto.setApellido(usuario.getApellido());
            dto.setCorreo(usuario.getCorreo());
            usuariosDTO.add(dto);
        }

        return usuariosDTO;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<String> eliminarUsuarioPorCorreo(String correo){
        if(usuarioRepository.findByCorreo(correo)== null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Usuario con correo " + correo + " no encontrado");
        }
        usuarioRepository.deleteByCorreo(correo);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }


}
