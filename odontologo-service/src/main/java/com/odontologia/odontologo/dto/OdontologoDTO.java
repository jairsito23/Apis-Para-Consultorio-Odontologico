package com.odontologia.odontologo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OdontologoDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100)
    private String apellido;

    @NotBlank(message = "La matrícula es obligatoria")
    @Size(max = 30)
    private String matricula;

    private String especialidad;
    private String telefono;
}
