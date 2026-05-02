package com.tuevento.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

public class FeedbackDTO {

    @Data
    public static class Request {
        @NotNull
        private Long inscripcionId;

        @NotNull @Min(1) @Max(5)
        private Integer calificacion;

        private String comentario;
    }

    @Data @Builder
    public static class Response {
        private Long id;
        private Long inscripcionId;
        private String usuarioNombre;
        private String eventoTitulo;
        private Integer calificacion;
        private String comentario;
        private LocalDateTime fechaEnvio;
    }
}