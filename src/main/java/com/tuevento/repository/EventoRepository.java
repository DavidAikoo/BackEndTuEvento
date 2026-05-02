package com.tuevento.repository;

import com.tuevento.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query(value = "SELECT * FROM eventos WHERE estado = :estado", nativeQuery = true)
    List<Evento> findByEstado(@Param("estado") String estado);

    @Query(value = "SELECT * FROM eventos WHERE organizador_id = :organizadorId", nativeQuery = true)
    List<Evento> findByOrganizadorId(@Param("organizadorId") Long organizadorId);

    @Query(value = "SELECT * FROM eventos WHERE estado = 'PUBLICADO' AND cupo_disponible > 0", nativeQuery = true)
    List<Evento> findPublicadosConCupo();

    @Query(value = "SELECT * FROM eventos WHERE LOWER(titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))", nativeQuery = true)
    List<Evento> findByTituloContaining(@Param("titulo") String titulo);
}