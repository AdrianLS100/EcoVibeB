package com.upc.ecovibeb.repositories;

import com.upc.ecovibeb.entities.ActividadesDiarias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ActividadesDiariasRepository extends JpaRepository<ActividadesDiarias, Long> {
    Optional<ActividadesDiarias> findByUsuarioIdAndFecha(Long usuarioId, LocalDate fecha);
}