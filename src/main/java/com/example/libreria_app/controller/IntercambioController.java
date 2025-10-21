package com.example.libreria_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.libreria_app.model.Intercambio;
import com.example.libreria_app.service.IntercambioService;
import com.example.libreria_app.dto.IntercambioCreateRequest;

@RestController
@RequestMapping("/intercambios")
public class IntercambioController {

    @Autowired
    private IntercambioService intercambioService;

    // Crear una nueva solicitud de intercambio
    @PostMapping
    public Intercambio crearSolicitud(@RequestBody IntercambioCreateRequest request) {
        return intercambioService.crearSolicitud(request.getSolicitanteId(), request.getLibroId(), request.getDestinatarioId());
    }

    // Aceptar una solicitud de intercambio
    @PostMapping("/{id}/aceptar")
    public Intercambio aceptarIntercambio(@PathVariable Long id) {
        return intercambioService.aceptarIntercambio(id);
    }

    // Rechazar una solicitud de intercambio
    @PostMapping("/{id}/rechazar")
    public Intercambio rechazarIntercambio(@PathVariable Long id) {
        return intercambioService.rechazarIntercambio(id);
    }

    // Listar intercambios por solicitante
    @GetMapping("/solicitante/{id}")
    public List<Intercambio> porSolicitante(@PathVariable Long id) {
        return intercambioService.listarPorSolicitante(id);
    }

    // Listar intercambios por destinatario
    @GetMapping("/destinatario/{id}")
    public List<Intercambio> porDestinatario(@PathVariable Long id) {
        return intercambioService.listarPorDestinatario(id);
    }

    // Historial de intercambios por libro
    @GetMapping("/libro/{id}")
    public List<Intercambio> historialPorLibro(@PathVariable Long id) {
        return intercambioService.historialPorLibro(id);
    }

    // Historial de intercambios entre dos usuarios (en ambas direcciones)
    @GetMapping("/historial/{usuarioId1}/{usuarioId2}")
    public List<Intercambio> historialEntre(@PathVariable Long usuarioId1, @PathVariable Long usuarioId2) {
        return intercambioService.historialEntreUsuarios(usuarioId1, usuarioId2);
    }
}
