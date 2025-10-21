package com.example.libreria_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.libreria_app.model.Intercambio;
import com.example.libreria_app.model.Intercambio.Estado;
import java.util.List;

/**
 * Repository for Intercambio entity.
 * Provides common query methods used by the service layer.
 */

@Repository
public interface IntercambioRepository extends JpaRepository<Intercambio, Long> {

    List<Intercambio> findBySolicitante_Id(Long id);

    List<Intercambio> findByDestinatario_Id(Long id);

	List<Intercambio> findByLibro_CodigoBarra(String codigoBarra);

    List<Intercambio> findByEstado(Estado estado);

    List<Intercambio> findBySolicitante_IdAndDestinatario_Id(Long solicitanteId, Long destinatarioId);
}
