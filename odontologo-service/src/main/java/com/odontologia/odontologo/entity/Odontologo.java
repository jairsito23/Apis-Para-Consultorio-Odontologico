package com.odontologia.odontologo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "odontologos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, unique = true, length = 30)
    private String matricula;

    @Column(length = 100)
    private String especialidad;

    @Column(length = 20)
    private String telefono;
}
