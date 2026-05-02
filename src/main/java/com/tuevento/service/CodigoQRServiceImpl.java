package com.tuevento.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.tuevento.exception.QRExpiradoException;
import com.tuevento.exception.QRYaUsadoException;
import com.tuevento.exception.ResourceNotFoundException;
import com.tuevento.model.CodigoQR;
import com.tuevento.model.Inscripcion;
import com.tuevento.repository.CodigoQRRepository;
import com.tuevento.service.CodigoQRService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CodigoQRServiceImpl implements CodigoQRService {

    private final CodigoQRRepository codigoQRRepository;

    @Override
    public CodigoQR generarQR(Inscripcion inscripcion, LocalDateTime expiracion) {
        String uuid = UUID.randomUUID().toString();
        String qrBase64 = generarImagenBase64(uuid);
        CodigoQR qr = CodigoQR.builder()
                .inscripcion(inscripcion)
                .codigoUnico(uuid)
                .urlQR("data:image/png;base64," + qrBase64)
                .fechaExpiracion(expiracion)
                .utilizado(false)
                .build();
        return codigoQRRepository.save(qr);
    }

    @Override
    public CodigoQR validarQR(String codigoUnico) {
        CodigoQR qr = codigoQRRepository.findByCodigoUnico(codigoUnico)
                .orElseThrow(() -> new ResourceNotFoundException("Código QR no encontrado"));
        if (qr.estaExpirado()) throw new QRExpiradoException();
        if (qr.getUtilizado()) throw new QRYaUsadoException();
        qr.setUtilizado(true);
        return codigoQRRepository.save(qr);
    }

    private String generarImagenBase64(String contenido) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(contenido, BarcodeFormat.QR_CODE, 300, 300);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", out);
            return Base64.getEncoder().encodeToString(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error generando QR: " + e.getMessage());
        }
    }
}