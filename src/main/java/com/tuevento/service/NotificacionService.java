package com.tuevento.service;

import com.tuevento.dto.NotificacionDTO;
import java.util.List;

public interface NotificacionService {
    NotificacionDTO.Response crear(NotificacionDTO.Request request);
    List<NotificacionDTO.Response> listarPorUsuario(Long usuarioId);
    List<NotificacionDTO.Response> listarNoLeidasPorUsuario(Long usuarioId);
    NotificacionDTO.Response marcarLeida(Long id);
    void eliminar(Long id);
}