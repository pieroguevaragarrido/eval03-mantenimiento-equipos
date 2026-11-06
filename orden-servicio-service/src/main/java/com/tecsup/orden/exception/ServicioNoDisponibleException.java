package com.tecsup.orden.exception;

/** Se lanza cuando otro microservicio no responde -> HTTP 503. */
public class ServicioNoDisponibleException extends RuntimeException {

    public ServicioNoDisponibleException(String message) {
        super(message);
    }
}
