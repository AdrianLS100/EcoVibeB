package com.upc.ecovibeb.controllers;

import com.upc.ecovibeb.dtos.*;
import com.upc.ecovibeb.security.entities.User;
import com.upc.ecovibeb.services.InstitucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/institucion")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
public class InstitucionController {

    @Autowired private InstitucionService service;

    @PostMapping("/crear")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> crear(@RequestBody InstitucionCrearRequest req, @AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.ok(service.crear(req, user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/unirse")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> unirse(@RequestBody InstitucionUnirseRequest req, @AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.ok(service.unirse(req, user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ROLE_INSTITUCION') or hasRole('USER')")
    public ResponseEntity<?> getDashboard(@AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.ok(service.getDashboard(user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}