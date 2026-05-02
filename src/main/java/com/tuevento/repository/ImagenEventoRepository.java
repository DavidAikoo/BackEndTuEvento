package com.tuevento.repository;

import com.tuevento.model.ImagenEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface ImagenEventoRepository extends JpaRepository<ImagenEvento, Long> {

    @Query(value = "SELECT * FROM imagenes_evento WHERE evento_id = :eventoId ORDER BY orden ASC", nativeQuery = true)
    List<ImagenEvento> findByEventoIdOrdenadas(@Param("eventoId") Long eventoId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM imagenes_evento WHERE evento_id = :eventoId", nativeQuery = true)
    void deleteByEventoId(@Param("eventoId") Long eventoId);
}