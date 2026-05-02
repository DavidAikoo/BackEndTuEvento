package com.tuevento.service;

import com.tuevento.dto.FeedbackDTO;
import java.util.List;

public interface FeedbackService {
    FeedbackDTO.Response enviar(FeedbackDTO.Request request);
    List<FeedbackDTO.Response> listarPorEvento(Long eventoId);
}