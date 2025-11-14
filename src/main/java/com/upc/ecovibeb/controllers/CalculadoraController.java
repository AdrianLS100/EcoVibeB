package com.upc.ecovibeb.controllers;

import com.upc.ecovibeb.dtos.CalculadoraPersonalDTO;
import com.upc.ecovibeb.interfaces.ICalculadoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
public class CalculadoraController {

    @Autowired
    private ICalculadoraService calculadoraService;

    @PostMapping("/calculadora")
    public ResponseEntity<CalculadoraPersonalDTO> calcularPersonal(
            @RequestBody CalculadoraPersonalDTO request) {

        CalculadoraPersonalDTO response = calculadoraService.calcularHuellaPersonal(request);
        return ResponseEntity.ok(response);
    }
}