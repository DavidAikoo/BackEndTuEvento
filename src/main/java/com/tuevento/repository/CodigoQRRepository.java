package com.tuevento.repository;

import com.tuevento.model.CodigoQR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CodigoQRRepository extends JpaRepository<CodigoQR, Long> {

    @Query(value = "SELECT * FROM codigos_qr WHERE codigo_unico = :codigoUnico", nativeQuery = true)
    Optional<CodigoQR> findByCodigoUnico(@Param("codigoUnico") String codigoUnico);

    @Query(value = "SELECT * FROM codigos_qr WHERE inscripcion_id = :inscripcionId", nativeQuery = true)
    Optional<CodigoQR> findByInscripcionId(@Param("inscripcionId") Long inscripcionId);
}