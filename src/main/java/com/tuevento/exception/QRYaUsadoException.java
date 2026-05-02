package com.tuevento.exception;

public class QRYaUsadoException extends RuntimeException {
    public QRYaUsadoException() {
        super("El código QR ya fue utilizado");
    }
}