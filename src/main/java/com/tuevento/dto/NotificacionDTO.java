package com.tuevento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

public class NotificacionDTO {

    @Data
    public static class Request {
        @NotNull(message = "El ID del usuario es obligatorio")
        private Long usuarioId;

        private Long eventoId;

        @NotBlank(message = "El título es obligatorio")
        private String titulo;

        @NotBlank(message = "El mensaje es obligatorio")
        private String mensaje;

        @NotBlank(message = "El tipo es obligatorio")
        private String tipo;
    }

    @Data @Builder
    public static class Response {
        private Long id;
        private Long usuarioId;
        private String usuarioNombre;
        private Long eventoId;
        private String eventoTitulo;
        private String titulo;
        private String mensaje;
        private String tipo;
        private Boolean leida;
        private LocalDateTime fechaCreacion;
    }
}