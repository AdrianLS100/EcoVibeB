package com.upc.ecovibeb.controllers;

import com.upc.ecovibeb.dtos.ComparativaPersonalDTO;
import com.upc.ecovibeb.security.entities.User;
import com.upc.ecovibeb.services.ComparativaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comparativa")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
public class ComparativaController {

    @Autowired
    private ComparativaService comparativaService;

    @GetMapping("/personal")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<ComparativaPersonalDTO> getComparativaPersonal(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(comparativaService.obtenerComparativa(user.getId()));
    }
}