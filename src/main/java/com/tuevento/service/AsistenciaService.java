package com.tuevento.service;

import com.tuevento.dto.AsistenciaDTO;
import java.util.List;

public interface AsistenciaService {
    AsistenciaDTO.Response marcarPorCheckbox(AsistenciaDTO.Request request);
    AsistenciaDTO.Response marcarPorQR(String codigoQR, Long staffId);
    List<AsistenciaDTO.Response> listarPorEvento(Long eventoId);
    long contarPresentes(Long eventoId);
}