package com.odontologia.historial.service;

import com.odontologia.historial.dto.HistorialClinicoDTO;
import com.odontologia.historial.entity.HistorialClinico;
import com.odontologia.historial.repository.HistorialClinicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistorialClinicoService {

    @Autowired
    private HistorialClinicoRepository historialRepository;

    public List<HistorialClinicoDTO> listarTodos() {
        return historialRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public HistorialClinicoDTO obtenerPorId(Long id) {
        HistorialClinico historial = historialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial clínico no encontrado con id: " + id));
        return convertirADTO(historial);
    }

    public List<HistorialClinicoDTO> listarPorPaciente(Long pacienteId) {
        return historialRepository.findByPacienteId(pacienteId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public HistorialClinicoDTO crear(HistorialClinicoDTO dto) {
        HistorialClinico historial = HistorialClinico.builder()
                .fecha(dto.getFecha())
                .diagnostico(dto.getDiagnostico())
                .tratamiento(dto.getTratamiento())
                .observaciones(dto.getObservaciones())
                .pacienteId(dto.getPacienteId())
                .odontologoId(dto.getOdontologoId())
                .build();

        historial = historialRepository.save(historial);
        return convertirADTO(historial);
    }

    public HistorialClinicoDTO actualizar(Long id, HistorialClinicoDTO dto) {
        HistorialClinico historial = historialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial clínico no encontrado con id: " + id));

        historial.setFecha(dto.getFecha());
        historial.setDiagnostico(dto.getDiagnostico());
        historial.setTratamiento(dto.getTratamiento());
        historial.setObservaciones(dto.getObservaciones());

        if (dto.getPacienteId() != null) {
            historial.setPacienteId(dto.getPacienteId());
        }
        if (dto.getOdontologoId() != null) {
            historial.setOdontologoId(dto.getOdontologoId());
        }

        historial = historialRepository.save(historial);
        return convertirADTO(historial);
    }

    public void eliminar(Long id) {
        HistorialClinico historial = historialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial clínico no encontrado con id: " + id));
        historialRepository.delete(historial);
    }

    private HistorialClinicoDTO convertirADTO(HistorialClinico historial) {
        HistorialClinicoDTO dto = new HistorialClinicoDTO();
        dto.setId(historial.getId());
        dto.setFecha(historial.getFecha());
        dto.setDiagnostico(historial.getDiagnostico());
        dto.setTratamiento(historial.getTratamiento());
        dto.setObservaciones(historial.getObservaciones());
        dto.setPacienteId(historial.getPacienteId());
        dto.setOdontologoId(historial.getOdontologoId());
        return dto;
    }
}
