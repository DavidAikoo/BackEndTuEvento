package com.tuevento.service;

import com.tuevento.dto.FeedbackDTO;
import com.tuevento.exception.ResourceNotFoundException;
import com.tuevento.model.Feedback;
import com.tuevento.model.Inscripcion;
import com.tuevento.repository.FeedbackRepository;
import com.tuevento.repository.InscripcionRepository;
import com.tuevento.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final InscripcionRepository inscripcionRepository;

    @Override
    public FeedbackDTO.Response enviar(FeedbackDTO.Request request) {
        if (feedbackRepository.existsByInscripcionId(request.getInscripcionId()))
            throw new RuntimeException("Ya enviaste feedback para esta inscripción");
        Inscripcion inscripcion = inscripcionRepository.findById(request.getInscripcionId())
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada"));
        Feedback feedback = Feedback.builder()
                .inscripcion(inscripcion)
                .calificacion(request.getCalificacion())
                .comentario(request.getComentario())
                .build();
        return toResponse(feedbackRepository.save(feedback));
    }

    @Override
    public List<FeedbackDTO.Response> listarPorEvento(Long eventoId) {
        return feedbackRepository.findByEventoId(eventoId)
                .stream().map(this::toResponse).toList();
    }

    private FeedbackDTO.Response toResponse(Feedback f) {
        return FeedbackDTO.Response.builder()
                .id(f.getId())
                .inscripcionId(f.getInscripcion().getId())
                .usuarioNombre(f.getInscripcion().getUsuario().getNombre()
                        + " " + f.getInscripcion().getUsuario().getApellido())
                .eventoTitulo(f.getInscripcion().getEvento().getTitulo())
                .calificacion(f.getCalificacion())
                .comentario(f.getComentario())
                .fechaEnvio(f.getFechaEnvio())
                .build();
    }
}