package com.tuevento.exception;

public class CupoAgotadoException extends RuntimeException {
    public CupoAgotadoException() {
        super("El evento no tiene cupo disponible");
    }
}