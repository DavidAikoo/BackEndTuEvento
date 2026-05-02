package com.tuevento.controller;

import com.tuevento.dto.CodigoQRDTO;
import com.tuevento.exception.ResourceNotFoundException;
import com.tuevento.model.CodigoQR;
import com.tuevento.repository.CodigoQRRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qr")
@RequiredArgsConstructor
public class CodigoQRController {

    private final CodigoQRRepository codigoQRRepository;

    @GetMapping("/{codigoUnico}")
    public ResponseEntity<CodigoQRDTO.Response> obtenerPorCodigo(@PathVariable String codigoUnico) {
        CodigoQR qr = codigoQRRepository.findByCodigoUnico(codigoUnico)
                .orElseThrow(() -> new ResourceNotFoundException("Código QR no encontrado"));
        return ResponseEntity.ok(toResponse(qr));
    }

    @GetMapping("/inscripcion/{inscripcionId}")
    public ResponseEntity<CodigoQRDTO.Response> obtenerPorInscripcion(@PathVariable Long inscripcionId) {
        CodigoQR qr = codigoQRRepository.findByInscripcionId(inscripcionId)
                .orElseThrow(() -> new ResourceNotFoundException("QR no encontrado para la inscripción: " + inscripcionId));
        return ResponseEntity.ok(toResponse(qr));
    }

    private CodigoQRDTO.Response toResponse(CodigoQR qr) {
        return CodigoQRDTO.Response.builder()
                .id(qr.getId())
                .inscripcionId(qr.getInscripcion().getId())
                .usuarioNombre(qr.getInscripcion().getUsuario().getNombre()
                        + " " + qr.getInscripcion().getUsuario().getApellido())
                .eventoTitulo(qr.getInscripcion().getEvento().getTitulo())
                .codigoUnico(qr.getCodigoUnico())
                .urlQR(qr.getUrlQR())
                .fechaGeneracion(qr.getFechaGeneracion())
                .fechaExpiracion(qr.getFechaExpiracion())
                .utilizado(qr.getUtilizado())
                .build();
    }
}