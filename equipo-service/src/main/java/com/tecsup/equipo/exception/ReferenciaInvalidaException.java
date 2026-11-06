package com.tecsup.equipo.exception;

/** Se lanza cuando el ID de otro microservicio no existe -> HTTP 400. */
public class ReferenciaInvalidaException extends RuntimeException {

    public ReferenciaInvalidaException(String message) {
        super(message);
    }
}
