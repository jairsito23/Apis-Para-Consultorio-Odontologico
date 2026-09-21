package com.odontologia.cita.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estado estado;

    @Column(length = 255)
    private String motivo;

    @Column(columnDefinition = "TEXT")
    private String notas;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "odontologo_id", nullable = false)
    private Long odontologoId;

    public enum Estado {
        PROGRAMADA,
        COMPLETADA,
        CANCELADA
    }
}
