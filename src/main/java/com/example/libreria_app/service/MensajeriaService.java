package com.example.libreria_app.service;

import java.time.LocalDateTime;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.libreria_app.model.Mensajeria;
import com.example.libreria_app.repository.MensajeriaRepository;
import com.example.libreria_app.dto.MensajeCreateRequest;

@Service
public class MensajeriaService {
    @Autowired
    private MensajeriaRepository mensajeriaRepository;

    public Mensajeria sendMessage(MensajeCreateRequest request) {
        if (request == null || request.getSenderId() == null || request.getRecipientId() == null || request.getContenido() == null) {
            throw new IllegalArgumentException("Campos obligatorios: senderId, recipientId, contenido");
        }
        Mensajeria m = new Mensajeria();
        m.setSenderId(request.getSenderId());
        m.setRecipientId(request.getRecipientId());
        m.setCuerpo_mensaje(request.getContenido().trim());
        m.setTimestamp(LocalDateTime.now());
        m.setLeido(false);
        m.setAsunto("Mensaje");
        m.setDestinatario(String.valueOf(request.getRecipientId()));
        return mensajeriaRepository.save(m);
    }

public List<Mensajeria> getMessagesForUser(Long userId) {
        return mensajeriaRepository.findBySenderIdOrRecipientIdOrderByTimestampAsc(userId, userId);
    }

    public List<Mensajeria> getConversation(Long userA, Long userB) {
        return mensajeriaRepository.findBySenderIdAndRecipientIdOrSenderIdAndRecipientIdOrderByTimestampAsc(userA, userB, userB, userA);
    }
}
