package com.odontologia.historial.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "historiales_clinicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialClinico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String diagnostico;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String tratamiento;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "odontologo_id", nullable = false)
    private Long odontologoId;
}
