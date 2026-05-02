package com.tuevento.exception;

public class QRExpiradoException extends RuntimeException {
    public QRExpiradoException() {
        super("El código QR ha expirado");
    }
}