package com.tuevento.controller;

import com.tuevento.dto.InscripcionDTO;
import com.tuevento.service.InscripcionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionService inscripcionService;

    @PostMapping
    public ResponseEntity<InscripcionDTO.Response> inscribir(@Valid @RequestBody InscripcionDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inscripcionService.inscribir(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionDTO.Response> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionService.obtenerPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<InscripcionDTO.Response>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(inscripcionService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<InscripcionDTO.Response>> listarPorEvento(@PathVariable Long eventoId) {
        return ResponseEntity.ok(inscripcionService.listarPorEvento(eventoId));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        inscripcionService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}