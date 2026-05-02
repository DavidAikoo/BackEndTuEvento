package com.tuevento.controller;

import com.tuevento.dto.ImagenEventoDTO;
import com.tuevento.service.ImagenEventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/imagenes")
@RequiredArgsConstructor
public class ImagenEventoController {

    private final ImagenEventoService imagenEventoService;

    @PostMapping
    public ResponseEntity<ImagenEventoDTO.Response> agregar(@Valid @RequestBody ImagenEventoDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(imagenEventoService.agregar(request));
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<ImagenEventoDTO.Response>> listarPorEvento(@PathVariable Long eventoId) {
        return ResponseEntity.ok(imagenEventoService.listarPorEvento(eventoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        imagenEventoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}