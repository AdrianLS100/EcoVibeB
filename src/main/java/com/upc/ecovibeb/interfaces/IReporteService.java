package com.upc.ecovibeb.interfaces;

import com.upc.ecovibeb.dtos.ReporteDTO;

public interface IReporteService {
    ReporteDTO calcularReporte(Long actividadId);
}