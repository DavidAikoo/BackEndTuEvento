package com.tuevento.service;

import com.tuevento.dto.UsuarioDTO;
import com.tuevento.enums.RolUsuario;
import com.tuevento.exception.ResourceNotFoundException;
import com.tuevento.model.Usuario;
import com.tuevento.repository.UsuarioRepository;
import com.tuevento.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDTO.Response crear(UsuarioDTO.Request request) {
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .passwordHash(request.getPassword())
                .telefono(request.getTelefono())
                .rol(RolUsuario.USUARIO)
                .activo(true)
                .build();
        return toResponse(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDTO.Response obtenerPorId(Long id) {
        return toResponse(findById(id));
    }

    @Override
    public List<UsuarioDTO.Response> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponse).toList();
    }

    @Override
    public UsuarioDTO.Response actualizar(Long id, UsuarioDTO.Request request) {
        Usuario usuario = findById(id);
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setTelefono(request.getTelefono());
        return toResponse(usuarioRepository.save(usuario));
    }

    @Override
    public void eliminar(Long id) {
        findById(id);
        usuarioRepository.deleteById(id);
    }

    private Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }

    private UsuarioDTO.Response toResponse(Usuario u) {
        return UsuarioDTO.Response.builder()
                .id(u.getId())
                .nombre(u.getNombre())
                .apellido(u.getApellido())
                .email(u.getEmail())
                .telefono(u.getTelefono())
                .fotoUrl(u.getFotoUrl())
                .rol(u.getRol())
                .activo(u.getActivo())
                .fechaCreacion(u.getFechaCreacion())
                .build();
    }
}