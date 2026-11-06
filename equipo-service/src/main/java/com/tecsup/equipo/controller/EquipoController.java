package com.tecsup.equipo.controller;

import com.tecsup.equipo.dto.EquipoDTO;
import com.tecsup.equipo.services.EquipoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoService service;

    public EquipoController(EquipoService service) {
        this.service = service;
    }

    @GetMapping
    public List<EquipoDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public EquipoDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/cliente/{idCliente}")
    public List<EquipoDTO> listarPorCliente(@PathVariable Long idCliente) {
        return service.listarPorCliente(idCliente);
    }

    @PostMapping
    public ResponseEntity<EquipoDTO> crear(@Valid @RequestBody EquipoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @PutMapping("/{id}")
    public EquipoDTO actualizar(@PathVariable Long id, @Valid @RequestBody EquipoDTO dto) {
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
