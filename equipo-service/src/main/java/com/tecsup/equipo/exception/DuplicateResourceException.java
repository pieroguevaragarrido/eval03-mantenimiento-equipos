package com.tecsup.equipo.exception;

/** Se lanza cuando se intenta registrar un valor unico repetido -> HTTP 409. */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
