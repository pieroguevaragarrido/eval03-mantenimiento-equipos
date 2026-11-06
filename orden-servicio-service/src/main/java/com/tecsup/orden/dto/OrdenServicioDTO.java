package com.tecsup.orden.dto;

import com.tecsup.orden.model.EstadoOrden;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrdenServicioDTO(
        Long id,

        @NotNull(message = "El idEquipo es obligatorio")
        @Positive(message = "El idEquipo debe ser un numero positivo")
        Long idEquipo,

        @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
        LocalDate fechaIngreso,

        @NotBlank(message = "La descripcion es obligatoria")
        @Size(max = 500, message = "La descripcion no puede superar 500 caracteres")
        String descripcion,

        @NotNull(message = "El costo estimado es obligatorio")
        @PositiveOrZero(message = "El costo estimado no puede ser negativo")
        BigDecimal costoEstimado,

        EstadoOrden estado) {
}
