package com.tuevento.service;

import com.tuevento.dto.InscripcionDTO;
import java.util.List;

public interface InscripcionService {
    InscripcionDTO.Response inscribir(InscripcionDTO.Request request);
    InscripcionDTO.Response obtenerPorId(Long id);
    List<InscripcionDTO.Response> listarPorUsuario(Long usuarioId);
    List<InscripcionDTO.Response> listarPorEvento(Long eventoId);
    void cancelar(Long id);
}