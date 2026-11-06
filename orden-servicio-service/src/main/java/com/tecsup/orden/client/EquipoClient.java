package com.tecsup.orden.client;

import com.tecsup.orden.exception.ReferenciaInvalidaException;
import com.tecsup.orden.exception.ServicioNoDisponibleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

/**
 * Cliente HTTP hacia equipo-service. Solo consulta si el ID existe;
 * no comparte tablas ni base de datos con ese servicio.
 */
@Component
public class EquipoClient {

    private final RestClient restClient;
    private final boolean validar;

    public EquipoClient(@Value("${servicios.equipo.url}") String baseUrl,
                        @Value("${servicios.validar-referencias:true}") boolean validar) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        this.restClient = RestClient.builder().baseUrl(baseUrl).requestFactory(factory).build();
        this.validar = validar;
    }

    public void verificarExiste(Long idEquipo) {
        if (!validar) {
            return;
        }
        try {
            restClient.get().uri("/api/equipos/{id}", idEquipo).retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ReferenciaInvalidaException("El equipo con id " + idEquipo + " no existe en equipo-service");
        } catch (ResourceAccessException ex) {
            throw new ServicioNoDisponibleException("equipo-service no esta disponible en este momento");
        }
    }
}
