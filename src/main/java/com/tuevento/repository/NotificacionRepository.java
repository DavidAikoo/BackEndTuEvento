package com.tuevento.repository;

import com.tuevento.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    @Query(value = "SELECT * FROM notificaciones WHERE usuario_id = :usuarioId", nativeQuery = true)
    List<Notificacion> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query(value = "SELECT * FROM notificaciones WHERE usuario_id = :usuarioId AND leida = false", nativeQuery = true)
    List<Notificacion> findNoLeidasByUsuarioId(@Param("usuarioId") Long usuarioId);
}