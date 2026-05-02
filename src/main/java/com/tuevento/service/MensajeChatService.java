package com.tuevento.service;

import com.tuevento.dto.MensajeChatDTO;
import java.util.List;

public interface MensajeChatService {
    MensajeChatDTO.Response enviar(MensajeChatDTO.Request request);
    List<MensajeChatDTO.Response> listarPorEvento(Long eventoId);
    MensajeChatDTO.Response editar(Long id, String nuevoContenido);
    void eliminar(Long id);
}