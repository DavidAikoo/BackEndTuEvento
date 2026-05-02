package com.tuevento.model;

import com.tuevento.enums.MetodoRegistro;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "asistencias")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inscripcion_id", nullable = false, unique = true)
    private Inscripcion inscripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Usuario staff;

    @Builder.Default
    @Column(nullable = false)
    private Boolean presente = false;

    @Column(name = "hora_entrada")
    private LocalDateTime horaEntrada;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_registro")
    private MetodoRegistro metodoRegistro;
}