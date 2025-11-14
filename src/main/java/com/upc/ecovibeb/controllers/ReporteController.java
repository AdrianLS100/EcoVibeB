package com.upc.ecovibeb.controllers;

import com.upc.ecovibeb.dtos.ReporteDTO;
import com.upc.ecovibeb.interfaces.IReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
public class ReporteController {

    @Autowired
    private IReporteService reporteService;

    public record ReporteRequest(Long actividadId) {}

    @PostMapping("/calcular")
    public ResponseEntity<ReporteDTO> calcularReporte(@RequestBody ReporteRequest request) {
        ReporteDTO reporte = reporteService.calcularReporte(request.actividadId());
        return ResponseEntity.ok(reporte);
    }
}