package com.tuevento.dto;

import com.tuevento.enums.EstadoEvento;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

public class EventoDTO {

    @Data
    public static class Request {
        @NotBlank(message = "El título es obligatorio")
        private String titulo;

        private String descripcion;

        @NotNull(message = "La fecha de inicio es obligatoria")
        private LocalDateTime fechaInicio;

        @NotNull(message = "La fecha de fin es obligatoria")
        private LocalDateTime fechaFin;

        @NotBlank(message = "El lugar es obligatorio")
        private String lugar;

        private String direccion;
        private Double latitud;
        private Double longitud;

        @NotNull @Min(value = 1, message = "El cupo debe ser mayor a 0")
        private Integer cupoMaximo;

        private EstadoEvento estado;
    }

    @Data @Builder
    public static class Response {
        private Long id;
        private String titulo;
        private String descripcion;
        private LocalDateTime fechaInicio;
        private LocalDateTime fechaFin;
        private String lugar;
        private String direccion;
        private Double latitud;
        private Double longitud;
        private Integer cupoMaximo;
        private Integer cupoDisponible;
        private EstadoEvento estado;
        private Long organizadorId;
        private String organizadorNombre;
        private LocalDateTime fechaCreacion;
    }
}