package com.tecsup.orden.services;

import com.tecsup.orden.client.EquipoClient;
import com.tecsup.orden.dto.OrdenServicioDTO;
import com.tecsup.orden.exception.ResourceNotFoundException;
import com.tecsup.orden.model.EstadoOrden;
import com.tecsup.orden.model.OrdenServicio;
import com.tecsup.orden.repository.OrdenServicioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrdenServicioService {

    private final OrdenServicioRepository repository;
    private final EquipoClient equipoClient;

    public OrdenServicioService(OrdenServicioRepository repository, EquipoClient equipoClient) {
        this.repository = repository;
        this.equipoClient = equipoClient;
    }

    @Transactional(readOnly = true)
    public List<OrdenServicioDTO> listar(EstadoOrden estado) {
        List<OrdenServicio> ordenes = (estado == null) ? repository.findAll() : repository.findByEstado(estado);
        return ordenes.stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public OrdenServicioDTO buscarPorId(Long id) {
        return toDto(obtener(id));
    }

    @Transactional(readOnly = true)
    public List<OrdenServicioDTO> listarPorEquipo(Long idEquipo) {
        return repository.findByIdEquipo(idEquipo).stream().map(this::toDto).toList();
    }

    @Transactional
    public OrdenServicioDTO crear(OrdenServicioDTO dto) {
        equipoClient.verificarExiste(dto.idEquipo());
        OrdenServicio orden = new OrdenServicio();
        aplicar(orden, dto);
        return toDto(repository.save(orden));
    }

    @Transactional
    public OrdenServicioDTO actualizar(Long id, OrdenServicioDTO dto) {
        OrdenServicio orden = obtener(id);
        if (!orden.getIdEquipo().equals(dto.idEquipo())) {
            equipoClient.verificarExiste(dto.idEquipo());
        }
        aplicar(orden, dto);
        return toDto(repository.save(orden));
    }

    @Transactional
    public OrdenServicioDTO cambiarEstado(Long id, EstadoOrden nuevoEstado) {
        OrdenServicio orden = obtener(id);
        orden.setEstado(nuevoEstado);
        return toDto(repository.save(orden));
    }

    @Transactional
    public void eliminar(Long id) {
        repository.delete(obtener(id));
    }

    private OrdenServicio obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden de servicio con id " + id + " no encontrada"));
    }

    private void aplicar(OrdenServicio o, OrdenServicioDTO dto) {
        o.setIdEquipo(dto.idEquipo());
        o.setFechaIngreso(dto.fechaIngreso() != null ? dto.fechaIngreso() : LocalDate.now());
        o.setDescripcion(dto.descripcion().trim());
        o.setCostoEstimado(dto.costoEstimado());
        o.setEstado(dto.estado() != null ? dto.estado() : EstadoOrden.RECIBIDO);
    }

    private OrdenServicioDTO toDto(OrdenServicio o) {
        return new OrdenServicioDTO(o.getId(), o.getIdEquipo(), o.getFechaIngreso(), o.getDescripcion(),
                o.getCostoEstimado(), o.getEstado());
    }
}
