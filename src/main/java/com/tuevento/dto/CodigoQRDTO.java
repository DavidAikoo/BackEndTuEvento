package com.tuevento.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

public class CodigoQRDTO {

    @Data
    public static class Request {
        @NotNull(message = "El ID de la inscripción es obligatorio")
        private Long inscripcionId;

        @NotNull(message = "La fecha de expiración es obligatoria")
        private LocalDateTime fechaExpiracion;
    }

    @Data @Builder
    public static class Response {
        private Long id;
        private Long inscripcionId;
        private String usuarioNombre;
        private String eventoTitulo;
        private String codigoUnico;
        private String urlQR;
        private LocalDateTime fechaGeneracion;
        private LocalDateTime fechaExpiracion;
        private Boolean utilizado;
    }
}