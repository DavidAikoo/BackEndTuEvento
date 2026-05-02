package com.tuevento.exception;

public class InscripcionDuplicadaException extends RuntimeException {
    public InscripcionDuplicadaException() {
        super("El usuario ya está inscrito en este evento");
    }
}