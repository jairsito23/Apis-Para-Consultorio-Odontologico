package com.odontologia.paciente.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PacienteDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100)
    private String apellido;

    @NotBlank(message = "El DNI es obligatorio")
    @Size(max = 20)
    private String dni;

    private String telefono;
    private String email;
    private String direccion;
    private LocalDate fechaNacimiento;
}
