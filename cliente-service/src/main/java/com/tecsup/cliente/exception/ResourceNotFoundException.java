package com.tecsup.cliente.exception;

/** Se lanza cuando un registro (por ID) no existe -> HTTP 404. */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
