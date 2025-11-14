package com.upc.ecovibeb.interfaces;

import com.upc.ecovibeb.dtos.ActividadesDiariasDTO;
import java.time.LocalDate;
import java.util.Optional;

public interface IActividadesDiariasService {

    ActividadesDiariasDTO crearActividad(Long usuarioId, LocalDate fecha, String descripcion);

    Optional<ActividadesDiariasDTO> obtenerPorId(Long actividadId);

    void eliminar(Long actividadId);
}