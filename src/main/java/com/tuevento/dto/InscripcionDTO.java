package com.tuevento.dto;

import com.tuevento.enums.EstadoInscripcion;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

public class InscripcionDTO {

    @Data
    public static class Request {
        @NotNull(message = "El ID del evento es obligatorio")
        private Long eventoId;

        @NotNull(message = "El ID del usuario es obligatorio")
        private Long usuarioId;
    }

    @Data @Builder
    public static class Response {
        private Long id;
        private Long usuarioId;
        private String usuarioNombre;
        private Long eventoId;
        private String eventoTitulo;
        private EstadoInscripcion estado;
        private LocalDateTime fechaInscripcion;
        private String codigoQR;
        private String urlQR;
        private LocalDateTime qrExpiracion;
    }
}