package com.upc.ecovibeb.controllers;

import com.upc.ecovibeb.dtos.RecomendacionDTO;
import com.upc.ecovibeb.security.entities.User;
import com.upc.ecovibeb.services.RecomendacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recomendaciones")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
public class RecomendacionController {

    @Autowired
    private RecomendacionService recomendacionService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR')")
    public ResponseEntity<List<RecomendacionDTO>> obtenerRecomendaciones(@AuthenticationPrincipal User userDetails) {
        return ResponseEntity.ok(recomendacionService.generarRecomendaciones(userDetails.getId()));
    }
}