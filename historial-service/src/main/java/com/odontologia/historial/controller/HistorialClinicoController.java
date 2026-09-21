package com.odontologia.historial.controller;

import com.odontologia.historial.dto.HistorialClinicoDTO;
import com.odontologia.historial.service.HistorialClinicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/historiales")
public class HistorialClinicoController {

    @Autowired
    private HistorialClinicoService historialService;

    @GetMapping
    public ResponseEntity<List<HistorialClinicoDTO>> listarTodos() {
        return ResponseEntity.ok(historialService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialClinicoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(historialService.obtenerPorId(id));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<HistorialClinicoDTO>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(historialService.listarPorPaciente(pacienteId));
    }

    @PostMapping
    public ResponseEntity<HistorialClinicoDTO> crear(@Valid @RequestBody HistorialClinicoDTO dto) {
        return new ResponseEntity<>(historialService.crear(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialClinicoDTO> actualizar(@PathVariable Long id,
                                                          @Valid @RequestBody HistorialClinicoDTO dto) {
        return ResponseEntity.ok(historialService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        historialService.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Historial clínico eliminado exitosamente"));
    }
}
