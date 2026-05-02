package com.tuevento.repository;

import com.tuevento.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    @Query(value = """
        SELECT a.* FROM asistencias a
        JOIN inscripciones i ON a.inscripcion_id = i.id
        WHERE i.evento_id = :eventoId
        """, nativeQuery = true)
    List<Asistencia> findByEventoId(@Param("eventoId") Long eventoId);

    @Query(value = "SELECT * FROM asistencias WHERE inscripcion_id = :inscripcionId", nativeQuery = true)
    Optional<Asistencia> findByInscripcionId(@Param("inscripcionId") Long inscripcionId);

    @Query(value = """
        SELECT COUNT(*) FROM asistencias a
        JOIN inscripciones i ON a.inscripcion_id = i.id
        WHERE i.evento_id = :eventoId AND a.presente = true
        """, nativeQuery = true)
    long countPresentesByEventoId(@Param("eventoId") Long eventoId);
}