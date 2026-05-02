package com.tuevento.dto;

import com.tuevento.enums.RolUsuario;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

public class UsuarioDTO {

    @Data
    public static class Request {
        @NotBlank(message = "El nombre es obligatorio")
        private String nombre;

        @NotBlank(message = "El apellido es obligatorio")
        private String apellido;

        @NotBlank @Email(message = "Email inválido")
        private String email;

        @NotBlank @Size(min = 6, message = "Mínimo 6 caracteres")
        private String password;

        private String telefono;
    }

    @Data @Builder
    public static class Response {
        private Long id;
        private String nombre;
        private String apellido;
        private String email;
        private String telefono;
        private String fotoUrl;
        private RolUsuario rol;
        private Boolean activo;
        private LocalDateTime fechaCreacion;
    }
}