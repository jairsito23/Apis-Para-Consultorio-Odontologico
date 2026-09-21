package com.odontologia.cita.service;

import com.odontologia.cita.dto.CitaDTO;
import com.odontologia.cita.entity.Cita;
import com.odontologia.cita.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public List<CitaDTO> listarTodas() {
        return citaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public CitaDTO obtenerPorId(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
        return convertirADTO(cita);
    }

    public List<CitaDTO> listarPorPaciente(Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<CitaDTO> listarPorOdontologo(Long odontologoId) {
        return citaRepository.findByOdontologoId(odontologoId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public CitaDTO crear(CitaDTO dto) {
        Cita cita = Cita.builder()
                .fechaHora(dto.getFechaHora())
                .estado(Cita.Estado.PROGRAMADA)
                .motivo(dto.getMotivo())
                .notas(dto.getNotas())
                .pacienteId(dto.getPacienteId())
                .odontologoId(dto.getOdontologoId())
                .build();

        cita = citaRepository.save(cita);
        return convertirADTO(cita);
    }

    public CitaDTO actualizar(Long id, CitaDTO dto) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));

        cita.setFechaHora(dto.getFechaHora());
        cita.setMotivo(dto.getMotivo());
        cita.setNotas(dto.getNotas());

        if (dto.getEstado() != null) {
            cita.setEstado(Cita.Estado.valueOf(dto.getEstado().toUpperCase()));
        }
        if (dto.getPacienteId() != null) {
            cita.setPacienteId(dto.getPacienteId());
        }
        if (dto.getOdontologoId() != null) {
            cita.setOdontologoId(dto.getOdontologoId());
        }

        cita = citaRepository.save(cita);
        return convertirADTO(cita);
    }

    public void eliminar(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
        citaRepository.delete(cita);
    }

    private CitaDTO convertirADTO(Cita cita) {
        CitaDTO dto = new CitaDTO();
        dto.setId(cita.getId());
        dto.setFechaHora(cita.getFechaHora());
        dto.setEstado(cita.getEstado().name());
        dto.setMotivo(cita.getMotivo());
        dto.setNotas(cita.getNotas());
        dto.setPacienteId(cita.getPacienteId());
        dto.setOdontologoId(cita.getOdontologoId());
        return dto;
    }
}
