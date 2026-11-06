package com.tecsup.equipo.services;

import com.tecsup.equipo.client.ClienteClient;
import com.tecsup.equipo.dto.EquipoDTO;
import com.tecsup.equipo.exception.DuplicateResourceException;
import com.tecsup.equipo.exception.ResourceNotFoundException;
import com.tecsup.equipo.model.Equipo;
import com.tecsup.equipo.repository.EquipoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipoService {

    private final EquipoRepository repository;
    private final ClienteClient clienteClient;

    public EquipoService(EquipoRepository repository, ClienteClient clienteClient) {
        this.repository = repository;
        this.clienteClient = clienteClient;
    }

    @Transactional(readOnly = true)
    public List<EquipoDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public EquipoDTO buscarPorId(Long id) {
        return toDto(obtener(id));
    }

    @Transactional(readOnly = true)
    public List<EquipoDTO> listarPorCliente(Long idCliente) {
        return repository.findByIdCliente(idCliente).stream().map(this::toDto).toList();
    }

    @Transactional
    public EquipoDTO crear(EquipoDTO dto) {
        if (repository.existsByCodigo(dto.codigo().trim())) {
            throw new DuplicateResourceException("Ya existe un equipo con el codigo " + dto.codigo());
        }
        clienteClient.verificarExiste(dto.idCliente());
        Equipo equipo = new Equipo();
        aplicar(equipo, dto);
        return toDto(repository.save(equipo));
    }

    @Transactional
    public EquipoDTO actualizar(Long id, EquipoDTO dto) {
        Equipo equipo = obtener(id);
        if (repository.existsByCodigoAndIdNot(dto.codigo().trim(), id)) {
            throw new DuplicateResourceException("Ya existe otro equipo con el codigo " + dto.codigo());
        }
        if (!equipo.getIdCliente().equals(dto.idCliente())) {
            clienteClient.verificarExiste(dto.idCliente());
        }
        aplicar(equipo, dto);
        return toDto(repository.save(equipo));
    }

    @Transactional
    public void eliminar(Long id) {
        repository.delete(obtener(id));
    }

    private Equipo obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo con id " + id + " no encontrado"));
    }

    private void aplicar(Equipo e, EquipoDTO dto) {
        e.setCodigo(dto.codigo().trim());
        e.setTipo(dto.tipo().trim());
        e.setMarca(dto.marca().trim());
        e.setModelo(dto.modelo().trim());
        e.setIdCliente(dto.idCliente());
    }

    private EquipoDTO toDto(Equipo e) {
        return new EquipoDTO(e.getId(), e.getCodigo(), e.getTipo(), e.getMarca(), e.getModelo(), e.getIdCliente());
    }
}
