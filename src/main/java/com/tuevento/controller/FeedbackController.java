package com.tuevento.controller;

import com.tuevento.dto.FeedbackDTO;
import com.tuevento.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<FeedbackDTO.Response> enviar(@Valid @RequestBody FeedbackDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.enviar(request));
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<FeedbackDTO.Response>> listarPorEvento(@PathVariable Long eventoId) {
        return ResponseEntity.ok(feedbackService.listarPorEvento(eventoId));
    }
}