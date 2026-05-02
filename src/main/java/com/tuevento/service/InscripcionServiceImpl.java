package com.tuevento.service;

import com.tuevento.dto.InscripcionDTO;
import com.tuevento.enums.EstadoInscripcion;
import com.tuevento.exception.*;
import com.tuevento.model.*;
import com.tuevento.repository.*;
import com.tuevento.service.InscripcionService;
import com.tuevento.service.CodigoQRService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CodigoQRService qrService;

    @Override
    @Transactional
    public InscripcionDTO.Response inscribir(InscripcionDTO.Request request) {
        if (inscripcionRepository.existsByUsuarioIdAndEventoId(request.getUsuarioId(), request.getEventoId()))
            throw new InscripcionDuplicadaException();

        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado"));

        if (evento.getCupoDisponible() <= 0)
            throw new CupoAgotadoException();

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Inscripcion inscripcion = Inscripcion.builder()
                .usuario(usuario)
                .evento(evento)
                .estado(EstadoInscripcion.CONFIRMADA)
                .build();
        inscripcion = inscripcionRepository.save(inscripcion);

        evento.setCupoDisponible(evento.getCupoDisponible() - 1);
        eventoRepository.save(evento);

        CodigoQR qr = qrService.generarQR(inscripcion, evento.getFechaFin());
        return toResponse(inscripcion, qr);
    }

    @Override
    public InscripcionDTO.Response obtenerPorId(Long id) {
        Inscripcion ins = inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada"));
        return toResponse(ins, ins.getCodigoQR());
    }

    @Override
    public List<InscripcionDTO.Response> listarPorUsuario(Long usuarioId) {
        return inscripcionRepository.findByUsuarioId(usuarioId).stream()
                .map(i -> toResponse(i, i.getCodigoQR())).toList();
    }

    @Override
    public List<InscripcionDTO.Response> listarPorEvento(Long eventoId) {
        return inscripcionRepository.findByEventoId(eventoId).stream()
                .map(i -> toResponse(i, i.getCodigoQR())).toList();
    }

    @Override
    @Transactional
    public void cancelar(Long id) {
        Inscripcion ins = inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada"));
        ins.setEstado(EstadoInscripcion.CANCELADA);
        ins.setFechaCancelacion(LocalDateTime.now());
        Evento evento = ins.getEvento();
        evento.setCupoDisponible(evento.getCupoDisponible() + 1);
        eventoRepository.save(evento);
        inscripcionRepository.save(ins);
    }

    private InscripcionDTO.Response toResponse(Inscripcion i, CodigoQR qr) {
        return InscripcionDTO.Response.builder()
                .id(i.getId())
                .usuarioId(i.getUsuario().getId())
                .usuarioNombre(i.getUsuario().getNombre() + " " + i.getUsuario().getApellido())
                .eventoId(i.getEvento().getId())
                .eventoTitulo(i.getEvento().getTitulo())
                .estado(i.getEstado())
                .fechaInscripcion(i.getFechaInscripcion())
                .codigoQR(qr != null ? qr.getCodigoUnico() : null)
                .urlQR(qr != null ? qr.getUrlQR() : null)
                .qrExpiracion(qr != null ? qr.getFechaExpiracion() : null)
                .build();
    }
}