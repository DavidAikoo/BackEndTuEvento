package com.tuevento.controller;

import com.tuevento.dto.MensajeChatDTO;
import com.tuevento.service.MensajeChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class MensajeChatController {

    private final MensajeChatService mensajeChatService;

    @PostMapping
    public ResponseEntity<MensajeChatDTO.Response> enviar(@Valid @RequestBody MensajeChatDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mensajeChatService.enviar(request));
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<MensajeChatDTO.Response>> listarPorEvento(@PathVariable Long eventoId) {
        return ResponseEntity.ok(mensajeChatService.listarPorEvento(eventoId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MensajeChatDTO.Response> editar(
            @PathVariable Long id,
            @RequestParam String contenido) {
        return ResponseEntity.ok(mensajeChatService.editar(id, contenido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mensajeChatService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}