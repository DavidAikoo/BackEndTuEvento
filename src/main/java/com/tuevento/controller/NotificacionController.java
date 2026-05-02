package com.tuevento.controller;

import com.tuevento.dto.NotificacionDTO;
import com.tuevento.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<NotificacionDTO.Response> crear(@Valid @RequestBody NotificacionDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.crear(request));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacionDTO.Response>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public ResponseEntity<List<NotificacionDTO.Response>> listarNoLeidas(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.listarNoLeidasPorUsuario(usuarioId));
    }

    @PatchMapping("/{id}/leer")
    public ResponseEntity<NotificacionDTO.Response> marcarLeida(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.marcarLeida(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}