package com.tecsup.cliente.services;

import com.tecsup.cliente.dto.ClienteDTO;
import com.tecsup.cliente.exception.DuplicateResourceException;
import com.tecsup.cliente.exception.ResourceNotFoundException;
import com.tecsup.cliente.model.Cliente;
import com.tecsup.cliente.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> listar() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ClienteDTO buscarPorId(Long id) {
        return toDto(obtener(id));
    }

    @Transactional
    public ClienteDTO crear(ClienteDTO dto) {
        if (repository.existsByDocumento(dto.documento())) {
            throw new DuplicateResourceException("Ya existe un cliente con el documento " + dto.documento());
        }
        Cliente cliente = new Cliente();
        aplicar(cliente, dto);
        return toDto(repository.save(cliente));
    }

    @Transactional
    public ClienteDTO actualizar(Long id, ClienteDTO dto) {
        Cliente cliente = obtener(id);
        if (repository.existsByDocumentoAndIdNot(dto.documento(), id)) {
            throw new DuplicateResourceException("Ya existe otro cliente con el documento " + dto.documento());
        }
        aplicar(cliente, dto);
        return toDto(repository.save(cliente));
    }

    @Transactional
    public void eliminar(Long id) {
        repository.delete(obtener(id));
    }

    private Cliente obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con id " + id + " no encontrado"));
    }

    private void aplicar(Cliente c, ClienteDTO dto) {
        c.setDocumento(dto.documento().trim());
        c.setNombres(dto.nombres().trim());
        c.setApellidos(dto.apellidos().trim());
        c.setCorreo(dto.correo().trim());
        c.setTelefono(dto.telefono() == null || dto.telefono().isBlank() ? null : dto.telefono().trim());
    }

    private ClienteDTO toDto(Cliente c) {
        return new ClienteDTO(c.getId(), c.getDocumento(), c.getNombres(), c.getApellidos(),
                c.getCorreo(), c.getTelefono());
    }
}
