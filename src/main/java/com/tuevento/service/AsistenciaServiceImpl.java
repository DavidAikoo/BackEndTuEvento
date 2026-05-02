package com.tuevento.service;

import com.tuevento.dto.AsistenciaDTO;
import com.tuevento.enums.MetodoRegistro;
import com.tuevento.exception.ResourceNotFoundException;
import com.tuevento.model.*;
import com.tuevento.repository.*;
import com.tuevento.service.AsistenciaService;
import com.tuevento.service.CodigoQRService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AsistenciaServiceImpl implements AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final InscripcionRepository inscripcionRepository;
    private final UsuarioRepository usuarioRepository;
    private final CodigoQRService qrService;

    @Override
    @Transactional
    public AsistenciaDTO.Response marcarPorCheckbox(AsistenciaDTO.Request request) {
        Inscripcion inscripcion = inscripcionRepository.findById(request.getInscripcionId())
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada"));
        Usuario staff = usuarioRepository.findById(request.getStaffId())
                .orElseThrow(() -> new ResourceNotFoundException("Staff no encontrado"));
        Asistencia asistencia = asistenciaRepository.findByInscripcionId(inscripcion.getId())
                .orElse(Asistencia.builder().inscripcion(inscripcion).build());
        asistencia.setPresente(true);
        asistencia.setHoraEntrada(LocalDateTime.now());
        asistencia.setMetodoRegistro(MetodoRegistro.CHECKBOX);
        asistencia.setStaff(staff);
        return toResponse(asistenciaRepository.save(asistencia));
    }

    @Override
    @Transactional
    public AsistenciaDTO.Response marcarPorQR(String codigoQR, Long staffId) {
        CodigoQR qr = qrService.validarQR(codigoQR);
        Usuario staff = usuarioRepository.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff no encontrado"));
        Asistencia asistencia = Asistencia.builder()
                .inscripcion(qr.getInscripcion())
                .staff(staff)
                .presente(true)
                .horaEntrada(LocalDateTime.now())
                .metodoRegistro(MetodoRegistro.QR)
                .build();
        return toResponse(asistenciaRepository.save(asistencia));
    }

    @Override
    public List<AsistenciaDTO.Response> listarPorEvento(Long eventoId) {
        return asistenciaRepository.findByEventoId(eventoId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public long contarPresentes(Long eventoId) {
        return asistenciaRepository.countPresentesByEventoId(eventoId);
    }

    private AsistenciaDTO.Response toResponse(Asistencia a) {
        return AsistenciaDTO.Response.builder()
                .id(a.getId())
                .inscripcionId(a.getInscripcion().getId())
                .usuarioNombre(a.getInscripcion().getUsuario().getNombre()
                        + " " + a.getInscripcion().getUsuario().getApellido())
                .presente(a.getPresente())
                .horaEntrada(a.getHoraEntrada())
                .metodoRegistro(a.getMetodoRegistro())
                .build();
    }
}