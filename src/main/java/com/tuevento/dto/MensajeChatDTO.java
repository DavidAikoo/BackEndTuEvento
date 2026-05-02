package com.tuevento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

public class MensajeChatDTO {

    @Data
    public static class Request {
        @NotNull(message = "El ID del remitente es obligatorio")
        private Long remitenteId;

        @NotNull(message = "El ID del evento es obligatorio")
        private Long eventoId;

        @NotBlank(message = "El contenido no puede estar vacío")
        private String contenido;
    }

    @Data @Builder
    public static class Response {
        private Long id;
        private Long remitenteId;
        private String remitenteNombre;
        private Long eventoId;
        private String eventoTitulo;
        private String contenido;
        private LocalDateTime fechaEnvio;
        private Boolean editado;
    }
}