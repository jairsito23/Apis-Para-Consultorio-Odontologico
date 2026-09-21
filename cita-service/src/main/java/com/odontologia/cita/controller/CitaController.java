package com.odontologia.cita.controller;

import com.odontologia.cita.dto.CitaDTO;
import com.odontologia.cita.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping
    public ResponseEntity<List<CitaDTO>> listarTodas() {
        return ResponseEntity.ok(citaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.obtenerPorId(id));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<CitaDTO>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(citaService.listarPorPaciente(pacienteId));
    }

    @GetMapping("/odontologo/{odontologoId}")
    public ResponseEntity<List<CitaDTO>> listarPorOdontologo(@PathVariable Long odontologoId) {
        return ResponseEntity.ok(citaService.listarPorOdontologo(odontologoId));
    }

    @PostMapping
    public ResponseEntity<CitaDTO> crear(@Valid @RequestBody CitaDTO dto) {
        return new ResponseEntity<>(citaService.crear(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CitaDTO dto) {
        return ResponseEntity.ok(citaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Cita eliminada exitosamente"));
    }
}
