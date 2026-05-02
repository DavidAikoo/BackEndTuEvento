package com.tuevento.service;

import com.tuevento.dto.NotificacionDTO;
import com.tuevento.exception.ResourceNotFoundException;
import com.tuevento.model.Evento;
import com.tuevento.model.Notificacion;
import com.tuevento.model.Usuario;
import com.tuevento.repository.EventoRepository;
import com.tuevento.repository.NotificacionRepository;
import com.tuevento.repository.UsuarioRepository;
import com.tuevento.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    @Override
    public NotificacionDTO.Response crear(NotificacionDTO.Request request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        Evento evento = null;
        if (request.getEventoId() != null) {
            evento = eventoRepository.findById(request.getEventoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado"));
        }
        Notificacion notificacion = Notificacion.builder()
                .usuario(usuario)
                .evento(evento)
                .titulo(request.getTitulo())
                .mensaje(request.getMensaje())
                .tipo(request.getTipo())
                .leida(false)
                .build();
        return toResponse(notificacionRepository.save(notificacion));
    }

    @Override
    public List<NotificacionDTO.Response> listarPorUsuario(Long usuarioId) {
        return notificacionRepository.findByUsuarioId(usuarioId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public List<NotificacionDTO.Response> listarNoLeidasPorUsuario(Long usuarioId) {
        return notificacionRepository.findNoLeidasByUsuarioId(usuarioId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public NotificacionDTO.Response marcarLeida(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada"));
        notificacion.setLeida(true);
        return toResponse(notificacionRepository.save(notificacion));
    }

    @Override
    public void eliminar(Long id) {
        if (!notificacionRepository.existsById(id))
            throw new ResourceNotFoundException("Notificación no encontrada con ID: " + id);
        notificacionRepository.deleteById(id);
    }

    private NotificacionDTO.Response toResponse(Notificacion n) {
        return NotificacionDTO.Response.builder()
                .id(n.getId())
                .usuarioId(n.getUsuario().getId())
                .usuarioNombre(n.getUsuario().getNombre() + " " + n.getUsuario().getApellido())
                .eventoId(n.getEvento() != null ? n.getEvento().getId() : null)
                .eventoTitulo(n.getEvento() != null ? n.getEvento().getTitulo() : null)
                .titulo(n.getTitulo())
                .mensaje(n.getMensaje())
                .tipo(n.getTipo())
                .leida(n.getLeida())
                .fechaCreacion(n.getFechaCreacion())
                .build();
    }
}