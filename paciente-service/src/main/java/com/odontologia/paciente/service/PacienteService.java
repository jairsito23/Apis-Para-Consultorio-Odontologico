package com.odontologia.paciente.service;

import com.odontologia.paciente.dto.PacienteDTO;
import com.odontologia.paciente.entity.Paciente;
import com.odontologia.paciente.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<PacienteDTO> listarTodos() {
        return pacienteRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public PacienteDTO obtenerPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));
        return convertirADTO(paciente);
    }

    public PacienteDTO crear(PacienteDTO dto) {
        if (pacienteRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("Ya existe un paciente con DNI: " + dto.getDni());
        }
        Paciente paciente = convertirAEntidad(dto);
        paciente = pacienteRepository.save(paciente);
        return convertirADTO(paciente);
    }

    public PacienteDTO actualizar(Long id, PacienteDTO dto) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));

        paciente.setNombre(dto.getNombre());
        paciente.setApellido(dto.getApellido());
        paciente.setDni(dto.getDni());
        paciente.setTelefono(dto.getTelefono());
        paciente.setEmail(dto.getEmail());
        paciente.setDireccion(dto.getDireccion());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());

        paciente = pacienteRepository.save(paciente);
        return convertirADTO(paciente);
    }

    public void eliminar(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));
        pacienteRepository.delete(paciente);
    }

    private PacienteDTO convertirADTO(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setNombre(paciente.getNombre());
        dto.setApellido(paciente.getApellido());
        dto.setDni(paciente.getDni());
        dto.setTelefono(paciente.getTelefono());
        dto.setEmail(paciente.getEmail());
        dto.setDireccion(paciente.getDireccion());
        dto.setFechaNacimiento(paciente.getFechaNacimiento());
        return dto;
    }

    private Paciente convertirAEntidad(PacienteDTO dto) {
        return Paciente.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .dni(dto.getDni())
                .telefono(dto.getTelefono())
                .email(dto.getEmail())
                .direccion(dto.getDireccion())
                .fechaNacimiento(dto.getFechaNacimiento())
                .build();
    }
}
