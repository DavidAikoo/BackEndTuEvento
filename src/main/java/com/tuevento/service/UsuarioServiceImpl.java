package com.tuevento.service;

import com.tuevento.dto.AuthDTO;
import com.tuevento.dto.UsuarioDTO;
import com.tuevento.enums.RolUsuario;
import com.tuevento.exception.ResourceNotFoundException;
import com.tuevento.model.Usuario;
import com.tuevento.repository.UsuarioRepository;
import com.tuevento.security.UserDetailsImpl;
import com.tuevento.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioDTO.Response crear(UsuarioDTO.Request request) {
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
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

    @Override
    public AuthDTO.RegisterResponse registrar(AuthDTO.RegisterRequest request) {
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .telefono(request.getTelefono())
                .rol(RolUsuario.USUARIO)
                .activo(true)
                .fechaCreacion(LocalDateTime.now())
                .build();
        Usuario saved = usuarioRepository.save(usuario);
        return AuthDTO.RegisterResponse.builder()
                .id(saved.getId())
                .nombre(saved.getNombre())
                .apellido(saved.getApellido())
                .email(saved.getEmail())
                .rol(saved.getRol())
                .fechaCreacion(saved.getFechaCreacion())
                .build();
    }

    @Override
    public UserDetailsImpl loadUserByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
        return UserDetailsImpl.build(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return loadUserByEmail(email);
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