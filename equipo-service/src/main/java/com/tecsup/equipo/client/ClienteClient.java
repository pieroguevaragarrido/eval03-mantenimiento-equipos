package com.tecsup.equipo.client;

import com.tecsup.equipo.exception.ReferenciaInvalidaException;
import com.tecsup.equipo.exception.ServicioNoDisponibleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

/**
 * Cliente HTTP hacia cliente-service. Solo consulta si el ID existe;
 * no comparte tablas ni base de datos con ese servicio.
 */
@Component
public class ClienteClient {

    private final RestClient restClient;
    private final boolean validar;

    public ClienteClient(@Value("${servicios.cliente.url}") String baseUrl,
                         @Value("${servicios.validar-referencias:true}") boolean validar) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        this.restClient = RestClient.builder().baseUrl(baseUrl).requestFactory(factory).build();
        this.validar = validar;
    }

    public void verificarExiste(Long idCliente) {
        if (!validar) {
            return;
        }
        try {
            restClient.get().uri("/api/clientes/{id}", idCliente).retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ReferenciaInvalidaException("El cliente con id " + idCliente + " no existe en cliente-service");
        } catch (ResourceAccessException ex) {
            throw new ServicioNoDisponibleException("cliente-service no esta disponible en este momento");
        }
    }
}
