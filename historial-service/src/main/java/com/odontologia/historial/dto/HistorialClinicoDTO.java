package com.odontologia.historial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class HistorialClinicoDTO {

    private Long id;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotBlank(message = "El diagnóstico es obligatorio")
    private String diagnostico;

    @NotBlank(message = "El tratamiento es obligatorio")
    private String tratamiento;

    private String observaciones;

    @NotNull(message = "El ID del paciente es obligatorio")
    private Long pacienteId;

    @NotNull(message = "El ID del odontólogo es obligatorio")
    private Long odontologoId;
}
