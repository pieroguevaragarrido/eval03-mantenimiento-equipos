package com.tecsup.orden.controller;

import com.tecsup.orden.dto.CambioEstadoDTO;
import com.tecsup.orden.dto.OrdenServicioDTO;
import com.tecsup.orden.model.EstadoOrden;
import com.tecsup.orden.services.OrdenServicioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes-servicio")
public class OrdenServicioController {

    private final OrdenServicioService service;

    public OrdenServicioController(OrdenServicioService service) {
        this.service = service;
    }

    /** GET /api/ordenes-servicio  o  GET /api/ordenes-servicio?estado=LISTO */
    @GetMapping
    public List<OrdenServicioDTO> listar(@RequestParam(required = false) EstadoOrden estado) {
        return service.listar(estado);
    }

    @GetMapping("/{id}")
    public OrdenServicioDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/equipo/{idEquipo}")
    public List<OrdenServicioDTO> listarPorEquipo(@PathVariable Long idEquipo) {
        return service.listarPorEquipo(idEquipo);
    }

    @PostMapping
    public ResponseEntity<OrdenServicioDTO> crear(@Valid @RequestBody OrdenServicioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @PutMapping("/{id}")
    public OrdenServicioDTO actualizar(@PathVariable Long id, @Valid @RequestBody OrdenServicioDTO dto) {
        return service.actualizar(id, dto);
    }

    /** PATCH /api/ordenes-servicio/{id}/estado  con body {"estado":"EN_REPARACION"} */
    @PatchMapping("/{id}/estado")
    public OrdenServicioDTO cambiarEstado(@PathVariable Long id, @Valid @RequestBody CambioEstadoDTO dto) {
        return service.cambiarEstado(id, dto.estado());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
