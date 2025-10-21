package com.example.libreria_app.controller;

import java.util.List;


import org.springframework.web.bind.annotation.*;

import com.example.libreria_app.model.Mensajeria;
import com.example.libreria_app.dto.MensajeCreateRequest;
import com.example.libreria_app.service.MensajeriaService;

@RestController
@RequestMapping("/mensajeria")
public class MensajeriaController {
    private final MensajeriaService mensajeriaService;


    public MensajeriaController(MensajeriaService mensajeriaService) {
        this.mensajeriaService = mensajeriaService;
    }

    @PostMapping("/send")
    public Mensajeria sendMessage(@RequestBody MensajeCreateRequest request) {
        return mensajeriaService.sendMessage(request);
    }

    @GetMapping("/user/{userId}")
    public List<Mensajeria> getMessagesForUser(@PathVariable Long userId) {
        return mensajeriaService.getMessagesForUser(userId);
    }

    @GetMapping("/conversation/{userA}/{userB}")
    public List<Mensajeria> getConversation(@PathVariable Long userA, @PathVariable Long userB) {
        return mensajeriaService.getConversation(userA, userB);
    }
}
