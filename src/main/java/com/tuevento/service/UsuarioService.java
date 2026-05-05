package com.tuevento.service;

import com.tuevento.dto.AuthDTO;
import com.tuevento.dto.UsuarioDTO;
import com.tuevento.security.UserDetailsImpl;
import java.util.List;

public interface UsuarioService {
    UsuarioDTO.Response crear(UsuarioDTO.Request request);
    UsuarioDTO.Response obtenerPorId(Long id);
    List<UsuarioDTO.Response> listarTodos();
    UsuarioDTO.Response actualizar(Long id, UsuarioDTO.Request request);
    void eliminar(Long id);
    AuthDTO.RegisterResponse registrar(AuthDTO.RegisterRequest request);
    UserDetailsImpl loadUserByEmail(String email);
}