package com.upc.ecovibeb.controllers;

import com.upc.ecovibeb.dtos.ActividadesDiariasDTO;
import com.upc.ecovibeb.interfaces.IActividadesDiariasService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus; // <-- ¡Añade este import!
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map; // <-- ¡Añade este import!
import java.util.Optional;

@RestController
@RequestMapping("/api") // (Usamos la ruta base /api/actividades)
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
public class ActividadesDiariasController {

    @Autowired
    private IActividadesDiariasService actividadesService;

    public record CrearActividadRequest(
            @NotNull Long usuarioId,
            @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            String descripcion
    ) {}

    @PostMapping("/crearactividad")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> crearActividad(@RequestBody CrearActividadRequest req) {
        try {
            ActividadesDiariasDTO dto = actividadesService.crearActividad(
                    req.usuarioId(), req.fecha(), req.descripcion()
            );
            return ResponseEntity.ok(dto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // HTTP 409
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            // Otros errores
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/{actividadId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ActividadesDiariasDTO> obtenerPorId(@PathVariable Long actividadId) {
        Optional<ActividadesDiariasDTO> dto = actividadesService.obtenerPorId(actividadId);
        return dto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{actividadId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long actividadId) {
        actividadesService.eliminar(actividadId);
        return ResponseEntity.noContent().build();
    }
}