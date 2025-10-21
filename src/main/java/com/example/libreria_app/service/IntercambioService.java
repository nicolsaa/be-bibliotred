package com.example.libreria_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.libreria_app.model.Intercambio;
import com.example.libreria_app.model.Intercambio.Estado;
import com.example.libreria_app.model.Libro;
import com.example.libreria_app.model.Usuario;
import com.example.libreria_app.repository.IntercambioRepository;
import com.example.libreria_app.repository.LibroRepository;
import com.example.libreria_app.repository.UsuarioRepository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service layer for Intercambio entity.
 * Encapsulates business logic for creating, accepting, rejecting and listing exchanges.
 */
@Service
public class IntercambioService {

    @Autowired
    private IntercambioRepository intercambioRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear una nueva solicitud de intercambio
    public Intercambio crearSolicitud(Long solicitanteId, Long libroId, Long destinatarioId) {
Libro libro = libroRepository.findById(libroId.toString()).orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        Usuario solicitante = usuarioRepository.findById(solicitanteId).orElseThrow(() -> new RuntimeException("Solicitante no encontrado"));
        Usuario destinatario = usuarioRepository.findById(destinatarioId).orElseThrow(() -> new RuntimeException("Destinatario no encontrado"));

        Intercambio i = new Intercambio();
        i.setLibro(libro);
        i.setSolicitante(solicitante);
        i.setDestinatario(destinatario);
        i.setEstado(Estado.SOLICITADO);
        i.setFechaSolicitud(LocalDateTime.now());

        return intercambioRepository.save(i);
    }

    // Aceptar una solicitud de intercambio
    public Intercambio aceptarIntercambio(Long id) {
        Intercambio i = intercambioRepository.findById(id).orElseThrow(() -> new RuntimeException("Intercambio no encontrado"));
        if (i.getEstado() == Estado.SOLICITADO) {
            i.setEstado(Estado.ACEPTADO);
            i.setFechaAceptacion(LocalDateTime.now());
            return intercambioRepository.save(i);
        }
        return i;
    }

    // Rechazar una solicitud de intercambio
    public Intercambio rechazarIntercambio(Long id) {
        Intercambio i = intercambioRepository.findById(id).orElseThrow(() -> new RuntimeException("Intercambio no encontrado"));
        if (i.getEstado() == Estado.SOLICITADO) {
            i.setEstado(Estado.RECHAZADO);
            i.setFechaAceptacion(LocalDateTime.now());
            return intercambioRepository.save(i);
        }
        return i;
    }

    // Listar intercambios por solicitante
    public List<Intercambio> listarPorSolicitante(Long solicitanteId) {
        return intercambioRepository.findBySolicitante_Id(solicitanteId);
    }

    // Listar intercambios por destinatario
    public List<Intercambio> listarPorDestinatario(Long destinatarioId) {
        return intercambioRepository.findByDestinatario_Id(destinatarioId);
    }

    // Historial de intercambios por libro
    public List<Intercambio> historialPorLibro(Long libroId) {
        Libro libro = libroRepository.findById(libroId.toString()).orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        return intercambioRepository.findByLibro_CodigoBarra(libro.getCodigoBarra());
    }

    // Historial de intercambios entre dos usuarios (en ambas direcciones)
    public List<Intercambio> historialEntreUsuarios(Long usuarioId1, Long usuarioId2) {
        List<Intercambio> a = intercambioRepository.findBySolicitante_IdAndDestinatario_Id(usuarioId1, usuarioId2);
        List<Intercambio> b = intercambioRepository.findBySolicitante_IdAndDestinatario_Id(usuarioId2, usuarioId1);
        List<Intercambio> result = new java.util.ArrayList<>();
        result.addAll(a);
        result.addAll(b);
        return result;
    }
}
