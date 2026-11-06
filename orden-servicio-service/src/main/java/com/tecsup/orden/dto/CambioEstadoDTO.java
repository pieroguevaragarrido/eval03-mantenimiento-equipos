package com.tecsup.orden.dto;

import com.tecsup.orden.model.EstadoOrden;
import jakarta.validation.constraints.NotNull;

public record CambioEstadoDTO(
        @NotNull(message = "El estado es obligatorio (RECIBIDO, EN_REPARACION, LISTO o ENTREGADO)")
        EstadoOrden estado) {
}
