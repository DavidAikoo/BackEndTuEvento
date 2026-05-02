package com.tuevento.service;

import com.tuevento.dto.EventoDTO;
import java.util.List;

public interface EventoService {
    EventoDTO.Response crear(EventoDTO.Request request, Long organizadorId);
    EventoDTO.Response obtenerPorId(Long id);
    List<EventoDTO.Response> listarPublicados();
    List<EventoDTO.Response> listarPorOrganizador(Long organizadorId);
    EventoDTO.Response actualizar(Long id, EventoDTO.Request request);
    EventoDTO.Response publicar(Long id);
    EventoDTO.Response cancelar(Long id);
    void eliminar(Long id);
}