package com.tuevento.service;

import com.tuevento.dto.UsuarioDTO;
import java.util.List;

public interface UsuarioService {
    UsuarioDTO.Response crear(UsuarioDTO.Request request);
    UsuarioDTO.Response obtenerPorId(Long id);
    List<UsuarioDTO.Response> listarTodos();
    UsuarioDTO.Response actualizar(Long id, UsuarioDTO.Request request);
    void eliminar(Long id);
}