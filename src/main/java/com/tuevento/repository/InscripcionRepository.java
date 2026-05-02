package com.tuevento.repository;

import com.tuevento.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    @Query(value = "SELECT * FROM inscripciones WHERE usuario_id = :usuarioId", nativeQuery = true)
    List<Inscripcion> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query(value = "SELECT * FROM inscripciones WHERE evento_id = :eventoId", nativeQuery = true)
    List<Inscripcion> findByEventoId(@Param("eventoId") Long eventoId);

    @Query(value = "SELECT * FROM inscripciones WHERE evento_id = :eventoId AND estado != 'CANCELADA'", nativeQuery = true)
    List<Inscripcion> findActivasByEventoId(@Param("eventoId") Long eventoId);

    @Query(value = "SELECT * FROM inscripciones WHERE usuario_id = :usuarioId AND evento_id = :eventoId", nativeQuery = true)
    Optional<Inscripcion> findByUsuarioIdAndEventoId(@Param("usuarioId") Long usuarioId, @Param("eventoId") Long eventoId);

    boolean existsByUsuarioIdAndEventoId(Long usuarioId, Long eventoId);
}