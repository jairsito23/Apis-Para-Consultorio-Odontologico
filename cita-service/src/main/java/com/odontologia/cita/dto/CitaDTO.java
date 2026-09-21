package com.odontologia.cita.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitaDTO {

    private Long id;

    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime fechaHora;

    private String estado;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    private String notas;

    @NotNull(message = "El ID del paciente es obligatorio")
    private Long pacienteId;

    @NotNull(message = "El ID del odontólogo es obligatorio")
    private Long odontologoId;
}
