package com.tuevento.service;

import com.tuevento.dto.EventoDTO;
import com.tuevento.enums.EstadoEvento;
import com.tuevento.exception.ResourceNotFoundException;
import com.tuevento.model.Evento;
import com.tuevento.model.Usuario;
import com.tuevento.repository.EventoRepository;
import com.tuevento.repository.UsuarioRepository;
import com.tuevento.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoServiceImpl implements EventoService {

    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public EventoDTO.Response crear(EventoDTO.Request request, Long organizadorId) {
        Usuario organizador = usuarioRepository.findById(organizadorId)
                .orElseThrow(() -> new ResourceNotFoundException("Organizador no encontrado"));
        Evento evento = Evento.builder()
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .lugar(request.getLugar())
                .direccion(request.getDireccion())
                .latitud(request.getLatitud())
                .longitud(request.getLongitud())
                .cupoMaximo(request.getCupoMaximo())
                .cupoDisponible(request.getCupoMaximo())
                .estado(EstadoEvento.BORRADOR)
                .organizador(organizador)
                .build();
        return toResponse(eventoRepository.save(evento));
    }

    @Override
    public EventoDTO.Response obtenerPorId(Long id) {
        return toResponse(findById(id));
    }

    @Override
    public List<EventoDTO.Response> listarPublicados() {
        return eventoRepository.findByEstado("PUBLICADO")
                .stream().map(this::toResponse).toList();
    }

    @Override
    public List<EventoDTO.Response> listarPorOrganizador(Long organizadorId) {
        return eventoRepository.findByOrganizadorId(organizadorId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public EventoDTO.Response actualizar(Long id, EventoDTO.Request request) {
        Evento evento = findById(id);
        evento.setTitulo(request.getTitulo());
        evento.setDescripcion(request.getDescripcion());
        evento.setFechaInicio(request.getFechaInicio());
        evento.setFechaFin(request.getFechaFin());
        evento.setLugar(request.getLugar());
        evento.setDireccion(request.getDireccion());
        evento.setCupoMaximo(request.getCupoMaximo());
        return toResponse(eventoRepository.save(evento));
    }

    @Override
    public EventoDTO.Response publicar(Long id) {
        Evento evento = findById(id);
        evento.setEstado(EstadoEvento.PUBLICADO);
        return toResponse(eventoRepository.save(evento));
    }

    @Override
    public EventoDTO.Response cancelar(Long id) {
        Evento evento = findById(id);
        evento.setEstado(EstadoEvento.CANCELADO);
        return toResponse(eventoRepository.save(evento));
    }

    @Override
    public void eliminar(Long id) {
        findById(id);
        eventoRepository.deleteById(id);
    }

    private Evento findById(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + id));
    }

    private EventoDTO.Response toResponse(Evento e) {
        return EventoDTO.Response.builder()
                .id(e.getId())
                .titulo(e.getTitulo())
                .descripcion(e.getDescripcion())
                .fechaInicio(e.getFechaInicio())
                .fechaFin(e.getFechaFin())
                .lugar(e.getLugar())
                .direccion(e.getDireccion())
                .latitud(e.getLatitud())
                .longitud(e.getLongitud())
                .cupoMaximo(e.getCupoMaximo())
                .cupoDisponible(e.getCupoDisponible())
                .estado(e.getEstado())
                .organizadorId(e.getOrganizador().getId())
                .organizadorNombre(e.getOrganizador().getNombre() + " " + e.getOrganizador().getApellido())
                .fechaCreacion(e.getFechaCreacion())
                .build();
    }
}