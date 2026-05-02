package com.tuevento.service;

import com.tuevento.dto.MensajeChatDTO;
import com.tuevento.exception.ResourceNotFoundException;
import com.tuevento.model.Evento;
import com.tuevento.model.MensajeChat;
import com.tuevento.model.Usuario;
import com.tuevento.repository.EventoRepository;
import com.tuevento.repository.MensajeChatRepository;
import com.tuevento.repository.UsuarioRepository;
import com.tuevento.service.MensajeChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MensajeChatServiceImpl implements MensajeChatService {

    private final MensajeChatRepository mensajeChatRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    @Override
    public MensajeChatDTO.Response enviar(MensajeChatDTO.Request request) {
        Usuario remitente = usuarioRepository.findById(request.getRemitenteId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado"));
        MensajeChat mensaje = MensajeChat.builder()
                .remitente(remitente)
                .evento(evento)
                .contenido(request.getContenido())
                .editado(false)
                .build();
        return toResponse(mensajeChatRepository.save(mensaje));
    }

    @Override
    public List<MensajeChatDTO.Response> listarPorEvento(Long eventoId) {
        return mensajeChatRepository.findByEventoIdOrdenados(eventoId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public MensajeChatDTO.Response editar(Long id, String nuevoContenido) {
        MensajeChat mensaje = mensajeChatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mensaje no encontrado"));
        mensaje.setContenido(nuevoContenido);
        mensaje.setEditado(true);
        return toResponse(mensajeChatRepository.save(mensaje));
    }

    @Override
    public void eliminar(Long id) {
        if (!mensajeChatRepository.existsById(id))
            throw new ResourceNotFoundException("Mensaje no encontrado con ID: " + id);
        mensajeChatRepository.deleteById(id);
    }

    private MensajeChatDTO.Response toResponse(MensajeChat m) {
        return MensajeChatDTO.Response.builder()
                .id(m.getId())
                .remitenteId(m.getRemitente().getId())
                .remitenteNombre(m.getRemitente().getNombre() + " " + m.getRemitente().getApellido())
                .eventoId(m.getEvento().getId())
                .eventoTitulo(m.getEvento().getTitulo())
                .contenido(m.getContenido())
                .fechaEnvio(m.getFechaEnvio())
                .editado(m.getEditado())
                .build();
    }
}