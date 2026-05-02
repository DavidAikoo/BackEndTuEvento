package com.tuevento.service;

import com.tuevento.dto.ImagenEventoDTO;
import java.util.List;

public interface ImagenEventoService {
    ImagenEventoDTO.Response agregar(ImagenEventoDTO.Request request);
    List<ImagenEventoDTO.Response> listarPorEvento(Long eventoId);
    void eliminar(Long id);
}