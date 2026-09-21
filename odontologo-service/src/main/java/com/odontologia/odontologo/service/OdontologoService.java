package com.odontologia.odontologo.service;

import com.odontologia.odontologo.dto.OdontologoDTO;
import com.odontologia.odontologo.entity.Odontologo;
import com.odontologia.odontologo.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OdontologoService {

    @Autowired
    private OdontologoRepository odontologoRepository;

    public List<OdontologoDTO> listarTodos() {
        return odontologoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public OdontologoDTO obtenerPorId(Long id) {
        Odontologo odontologo = odontologoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Odontólogo no encontrado con id: " + id));
        return convertirADTO(odontologo);
    }

    public OdontologoDTO crear(OdontologoDTO dto) {
        if (odontologoRepository.existsByMatricula(dto.getMatricula())) {
            throw new IllegalArgumentException("Ya existe un odontólogo con matrícula: " + dto.getMatricula());
        }
        Odontologo odontologo = convertirAEntidad(dto);
        odontologo = odontologoRepository.save(odontologo);
        return convertirADTO(odontologo);
    }

    public OdontologoDTO actualizar(Long id, OdontologoDTO dto) {
        Odontologo odontologo = odontologoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Odontólogo no encontrado con id: " + id));

        odontologo.setNombre(dto.getNombre());
        odontologo.setApellido(dto.getApellido());
        odontologo.setMatricula(dto.getMatricula());
        odontologo.setEspecialidad(dto.getEspecialidad());
        odontologo.setTelefono(dto.getTelefono());

        odontologo = odontologoRepository.save(odontologo);
        return convertirADTO(odontologo);
    }

    public void eliminar(Long id) {
        Odontologo odontologo = odontologoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Odontólogo no encontrado con id: " + id));
        odontologoRepository.delete(odontologo);
    }

    private OdontologoDTO convertirADTO(Odontologo odontologo) {
        OdontologoDTO dto = new OdontologoDTO();
        dto.setId(odontologo.getId());
        dto.setNombre(odontologo.getNombre());
        dto.setApellido(odontologo.getApellido());
        dto.setMatricula(odontologo.getMatricula());
        dto.setEspecialidad(odontologo.getEspecialidad());
        dto.setTelefono(odontologo.getTelefono());
        return dto;
    }

    private Odontologo convertirAEntidad(OdontologoDTO dto) {
        return Odontologo.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .matricula(dto.getMatricula())
                .especialidad(dto.getEspecialidad())
                .telefono(dto.getTelefono())
                .build();
    }
}
