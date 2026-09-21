package com.odontologia.odontologo.controller;

import com.odontologia.odontologo.dto.OdontologoDTO;
import com.odontologia.odontologo.service.OdontologoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/odontologos")
public class OdontologoController {

    @Autowired
    private OdontologoService odontologoService;

    @GetMapping
    public ResponseEntity<List<OdontologoDTO>> listarTodos() {
        return ResponseEntity.ok(odontologoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontologoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(odontologoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<OdontologoDTO> crear(@Valid @RequestBody OdontologoDTO dto) {
        return new ResponseEntity<>(odontologoService.crear(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OdontologoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody OdontologoDTO dto) {
        return ResponseEntity.ok(odontologoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        odontologoService.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Odontólogo eliminado exitosamente"));
    }
}
