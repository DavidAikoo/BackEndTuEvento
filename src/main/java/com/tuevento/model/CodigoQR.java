package com.tuevento.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "codigos_qr")
public class CodigoQR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inscripcion_id", nullable = false, unique = true)
    private Inscripcion inscripcion;

    @Column(name = "codigo_unico", nullable = false, unique = true, length = 36)
    private String codigoUnico;

    @Column(name = "url_qr", columnDefinition = "TEXT")
    private String urlQR;

    @Column(name = "fecha_generacion", nullable = false, updatable = false)
    private LocalDateTime fechaGeneracion;

    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDateTime fechaExpiracion;

    @Builder.Default
    @Column(nullable = false)
    private Boolean utilizado = false;

    @PrePersist
    public void prePersist() {
        this.fechaGeneracion = LocalDateTime.now();
        if (this.codigoUnico == null) {
            this.codigoUnico = UUID.randomUUID().toString();
        }
    }

    public boolean estaExpirado() {
        return LocalDateTime.now().isAfter(this.fechaExpiracion);
    }
}