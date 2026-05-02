package com.tuevento.repository;

import com.tuevento.model.MensajeChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MensajeChatRepository extends JpaRepository<MensajeChat, Long> {

    @Query(value = "SELECT * FROM mensajes_chat WHERE evento_id = :eventoId ORDER BY fecha_envio ASC", nativeQuery = true)
    List<MensajeChat> findByEventoIdOrdenados(@Param("eventoId") Long eventoId);

    @Query(value = "SELECT * FROM mensajes_chat WHERE remitente_id = :remitenteId", nativeQuery = true)
    List<MensajeChat> findByRemitenteId(@Param("remitenteId") Long remitenteId);

    @Query(value = "SELECT COUNT(*) FROM mensajes_chat WHERE evento_id = :eventoId", nativeQuery = true)
    long countByEventoId(@Param("eventoId") Long eventoId);
}