package com.upc.ecovibeb.repositories;

import com.upc.ecovibeb.entities.Institucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InstitucionRepository extends JpaRepository<Institucion, Long> {
    Optional<Institucion> findByCodigoInvitacion(String codigoInvitacion);
    Optional<Institucion> findByAdminId(Long adminId);
}