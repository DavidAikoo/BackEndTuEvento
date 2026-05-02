package com.tuevento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

public class ImagenEventoDTO {

    @Data
    public static class Request {
        @NotNull(message = "El ID del evento es obligatorio")
        private Long eventoId;

        @NotBlank(message = "La URL es obligatoria")
        private String url;

        private String descripcion;

        @NotNull(message = "El orden es obligatorio")
        private Integer orden;
    }

    @Data @Builder
    public static class Response {
        private Long id;
        private Long eventoId;
        private String url;
        private String descripcion;
        private Integer orden;
        private LocalDateTime fechaSubida;
    }
}