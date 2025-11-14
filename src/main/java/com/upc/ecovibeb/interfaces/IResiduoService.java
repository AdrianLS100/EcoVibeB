package com.upc.ecovibeb.interfaces;

import com.upc.ecovibeb.dtos.ResiduoDTO;

import java.util.List;

public interface IResiduoService {
    ResiduoDTO crear(ResiduoDTO dto);
    List<ResiduoDTO> listarPorActividad(Long actividadId);
    void eliminar(Long residuoId);
}