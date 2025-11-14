package com.upc.ecovibeb.controllers;

import com.upc.ecovibeb.dtos.FactoresEmisionDTO;
import com.upc.ecovibeb.interfaces.IFactoresEmisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factores")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
public class FactoresEmisionController {

    @Autowired
    private IFactoresEmisionService factoresService;

    public record BuscarFactorRequest(String categoria, String subcategoria, String unidadBase) {}

    @PostMapping("/buscar")
    public ResponseEntity<FactoresEmisionDTO> buscarVigente(@RequestBody BuscarFactorRequest request) {
        return factoresService.buscarVigente(request.categoria(), request.subcategoria(), request.unidadBase())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<FactoresEmisionDTO> crear(@RequestBody FactoresEmisionDTO dto) {
        return ResponseEntity.ok(factoresService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<FactoresEmisionDTO>> listar() {
        return ResponseEntity.ok(factoresService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FactoresEmisionDTO> obtenerPorId(@PathVariable Long id) {
        return factoresService.obtener(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FactoresEmisionDTO> actualizar(@PathVariable Long id, @RequestBody FactoresEmisionDTO dto) {
        return ResponseEntity.ok(factoresService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        factoresService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}