package com.tuevento.model;

import com.tuevento.enums.EstadoInscripcion;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(
        name = "inscripciones",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "evento_id"})
)
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoInscripcion estado = EstadoInscripcion.PENDIENTE;

    @Column(name = "fecha_inscripcion", nullable = false, updatable = false)
    private LocalDateTime fechaInscripcion;

    @Column(name = "fecha_cancelacion")
    private LocalDateTime fechaCancelacion;

    @OneToOne(mappedBy = "inscripcion", cascade = CascadeType.ALL, orphanRemoval = true)
    private CodigoQR codigoQR;

    @OneToOne(mappedBy = "inscripcion", cascade = CascadeType.ALL)
    private Asistencia asistencia;

    @OneToOne(mappedBy = "inscripcion", cascade = CascadeType.ALL)
    private Feedback feedback;

    @PrePersist
    public void prePersist() {
        this.fechaInscripcion = LocalDateTime.now();
    }
}