package com.upc.ecovibeb.interfaces;

import com.upc.ecovibeb.dtos.EnergiaDTO;

import java.util.List;

public interface IEnergiaService {
    EnergiaDTO crear(EnergiaDTO dto);
    List<EnergiaDTO> listarPorActividad(Long actividadId);
    void eliminar(Long energiaId);
}