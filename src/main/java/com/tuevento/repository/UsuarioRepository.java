package com.tuevento.repository;

import com.tuevento.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM usuarios WHERE email = :email", nativeQuery = true)
    Optional<Usuario> findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM usuarios WHERE rol = :rol AND activo = true", nativeQuery = true)
    List<Usuario> findActivosByRol(@Param("rol") String rol);

    @Query(value = "SELECT * FROM usuarios WHERE LOWER(nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))", nativeQuery = true)
    List<Usuario> findByNombreContaining(@Param("nombre") String nombre);

    boolean existsByEmail(String email);
}