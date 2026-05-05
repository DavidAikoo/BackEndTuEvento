package com.tuevento.dto;

import com.tuevento.enums.RolUsuario;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

public class AuthDTO {

    @Data
    public static class LoginRequest {
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Email inválido")
        private String email;

        @NotBlank(message = "La contraseña es obligatoria")
        private String password;
    }

    @Data
    public static class RegisterRequest {
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

    @Data
    @Builder
    public static class LoginResponse {
        private String token;
        private String type = "Bearer";
        private Long id;
        private String nombre;
        private String apellido;
        private String email;
        private RolUsuario rol;
    }

    @Data
    @Builder
    public static class RegisterResponse {
        private Long id;
        private String nombre;
        private String apellido;
        private String email;
        private RolUsuario rol;
        private LocalDateTime fechaCreacion;
    }
}
