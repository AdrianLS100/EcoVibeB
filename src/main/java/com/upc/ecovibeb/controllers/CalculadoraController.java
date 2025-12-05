package com.upc.ecovibeb.controllers;

import com.upc.ecovibeb.dtos.CalculadoraPersonalDTO;
import com.upc.ecovibeb.interfaces.ICalculadoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
public class CalculadoraController {

    @Autowired
    private ICalculadoraService calculadoraService;

    @PostMapping("/calculadora")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<CalculadoraPersonalDTO> calcularPersonal(
            @RequestBody CalculadoraPersonalDTO request) {

        CalculadoraPersonalDTO response = calculadoraService.calcularHuellaPersonal(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/calculadora/familiar/{familiaId}")
    @PreAuthorize("hasRole('FAMILIAR') or hasRole('USER')")
    public ResponseEntity<Double> calcularHuellaFamiliar(@PathVariable Long familiaId) {
        return ResponseEntity.ok(calculadoraService.calcularHuellaFamiliar(familiaId));
    }
}