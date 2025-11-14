package com.upc.ecovibeb.controllers;

import com.upc.ecovibeb.dtos.TransporteDTO;
import com.upc.ecovibeb.interfaces.ITransporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
public class TransporteController {

    @Autowired
    private ITransporteService transporteService;

    @PostMapping("/transporte")
    public ResponseEntity<TransporteDTO> crear(@RequestBody TransporteDTO dto) {
        TransporteDTO creado = transporteService.crear(dto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/por-actividad/{actividadId}")
    public ResponseEntity<List<TransporteDTO>> listarPorActividad(@PathVariable Long actividadId) {
        List<TransporteDTO> lista = transporteService.listarPorActividad(actividadId);
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/transporte/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        transporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}