package com.tuevento.dto;

import com.tuevento.enums.MetodoRegistro;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

public class AsistenciaDTO {

    @Data
    public static class Request {
        @NotNull
        private Long inscripcionId;

        @NotNull
        private Long staffId;

        @NotNull
        private MetodoRegistro metodo;
    }

    @Data @Builder
    public static class Response {
        private Long id;
        private Long inscripcionId;
        private String usuarioNombre;
        private Boolean presente;
        private LocalDateTime horaEntrada;
        private MetodoRegistro metodoRegistro;
    }
}