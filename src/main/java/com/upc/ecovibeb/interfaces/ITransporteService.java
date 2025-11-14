package com.upc.ecovibeb.interfaces;

import com.upc.ecovibeb.dtos.TransporteDTO;

import java.util.List;

public interface ITransporteService {
    TransporteDTO crear(TransporteDTO dto);
    List<TransporteDTO> listarPorActividad(Long actividadId);
    void eliminar(Long transporteId);
}