package com.tuevento.service;

import com.tuevento.dto.ImagenEventoDTO;
import com.tuevento.exception.ResourceNotFoundException;
import com.tuevento.model.Evento;
import com.tuevento.model.ImagenEvento;
import com.tuevento.repository.EventoRepository;
import com.tuevento.repository.ImagenEventoRepository;
import com.tuevento.service.ImagenEventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImagenEventoServiceImpl implements ImagenEventoService {

    private final ImagenEventoRepository imagenEventoRepository;
    private final EventoRepository eventoRepository;

    @Override
    public ImagenEventoDTO.Response agregar(ImagenEventoDTO.Request request) {
        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado"));
        ImagenEvento imagen = ImagenEvento.builder()
                .evento(evento)
                .url(request.getUrl())
                .descripcion(request.getDescripcion())
                .orden(request.getOrden())
                .build();
        return toResponse(imagenEventoRepository.save(imagen));
    }

    @Override
    public List<ImagenEventoDTO.Response> listarPorEvento(Long eventoId) {
        return imagenEventoRepository.findByEventoIdOrdenadas(eventoId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public void eliminar(Long id) {
        if (!imagenEventoRepository.existsById(id))
            throw new ResourceNotFoundException("Imagen no encontrada con ID: " + id);
        imagenEventoRepository.deleteById(id);
    }

    private ImagenEventoDTO.Response toResponse(ImagenEvento i) {
        return ImagenEventoDTO.Response.builder()
                .id(i.getId())
                .eventoId(i.getEvento().getId())
                .url(i.getUrl())
                .descripcion(i.getDescripcion())
                .orden(i.getOrden())
                .fechaSubida(i.getFechaSubida())
                .build();
    }
}