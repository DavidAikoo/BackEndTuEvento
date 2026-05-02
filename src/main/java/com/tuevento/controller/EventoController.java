package com.tuevento.controller;

import com.tuevento.dto.EventoDTO;
import com.tuevento.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @PostMapping
    public ResponseEntity<EventoDTO.Response> crear(
            @Valid @RequestBody EventoDTO.Request request,
            @RequestParam Long organizadorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.crear(request, organizadorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO.Response> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<EventoDTO.Response>> listarPublicados() {
        return ResponseEntity.ok(eventoService.listarPublicados());
    }

    @GetMapping("/organizador/{organizadorId}")
    public ResponseEntity<List<EventoDTO.Response>> listarPorOrganizador(@PathVariable Long organizadorId) {
        return ResponseEntity.ok(eventoService.listarPorOrganizador(organizadorId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EventoDTO.Request request) {
        return ResponseEntity.ok(eventoService.actualizar(id, request));
    }

    @PatchMapping("/{id}/publicar")
    public ResponseEntity<EventoDTO.Response> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.publicar(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<EventoDTO.Response> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.cancelar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eventoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}