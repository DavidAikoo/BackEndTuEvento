package com.tuevento.repository;

import com.tuevento.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query(value = """
        SELECT f.* FROM feedbacks f
        JOIN inscripciones i ON f.inscripcion_id = i.id
        WHERE i.evento_id = :eventoId
        """, nativeQuery = true)
    List<Feedback> findByEventoId(@Param("eventoId") Long eventoId);

    @Query(value = "SELECT * FROM feedbacks WHERE inscripcion_id = :inscripcionId", nativeQuery = true)
    Optional<Feedback> findByInscripcionId(@Param("inscripcionId") Long inscripcionId);

    boolean existsByInscripcionId(Long inscripcionId);
}