package com.odontologia.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank(message = "El email es obligatorio")
    @Email
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6)
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    private String rol;
}
