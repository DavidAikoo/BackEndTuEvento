package com.tuevento.service;

import com.tuevento.model.CodigoQR;
import com.tuevento.model.Inscripcion;
import java.time.LocalDateTime;

public interface CodigoQRService {
    CodigoQR generarQR(Inscripcion inscripcion, LocalDateTime expiracion);
    CodigoQR validarQR(String codigoUnico);
}