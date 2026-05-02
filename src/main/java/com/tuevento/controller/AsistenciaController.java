package com.tuevento.controller;

import com.tuevento.dto.AsistenciaDTO;
import com.tuevento.service.AsistenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/asistencias")
@RequiredArgsConstructor
public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    @PostMapping("/marcar")
    public ResponseEntity<AsistenciaDTO.Response> marcarCheckbox(@Valid @RequestBody AsistenciaDTO.Request request) {
        return ResponseEntity.ok(asistenciaService.marcarPorCheckbox(request));
    }

    @PostMapping("/validar-qr")
    public ResponseEntity<AsistenciaDTO.Response> validarQR(
            @RequestParam String codigoQR,
            @RequestParam Long staffId) {
        return ResponseEntity.ok(asistenciaService.marcarPorQR(codigoQR, staffId));
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<AsistenciaDTO.Response>> listarPorEvento(@PathVariable Long eventoId) {
        return ResponseEntity.ok(asistenciaService.listarPorEvento(eventoId));
    }

    @GetMapping("/evento/{eventoId}/count")
    public ResponseEntity<Map<String, Long>> contarPresentes(@PathVariable Long eventoId) {
        return ResponseEntity.ok(Map.of("presentes", asistenciaService.contarPresentes(eventoId)));
    }
}