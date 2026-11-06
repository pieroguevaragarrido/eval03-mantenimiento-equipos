package com.tecsup.orden.repository;

import com.tecsup.orden.model.EstadoOrden;
import com.tecsup.orden.model.OrdenServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenServicioRepository extends JpaRepository<OrdenServicio, Long> {

    List<OrdenServicio> findByIdEquipo(Long idEquipo);

    List<OrdenServicio> findByEstado(EstadoOrden estado);
}
