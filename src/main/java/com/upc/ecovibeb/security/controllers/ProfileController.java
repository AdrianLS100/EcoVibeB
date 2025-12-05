package com.upc.ecovibeb.security.controllers;

import com.upc.ecovibeb.security.dtos.ProfileDTO;
import com.upc.ecovibeb.security.dtos.UpdatePasswordDTO;
import com.upc.ecovibeb.security.entities.User;
import com.upc.ecovibeb.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<ProfileDTO> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        return ResponseEntity.ok(userService.getProfile(user.getId()));
    }

    @PostMapping("/change-password")
    @PreAuthorize("hasRole('USER') or hasRole('FAMILIAR') or hasRole('INSTITUCION')")
    public ResponseEntity<?> changeMyPassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdatePasswordDTO request) {
        try {
            User user = (User) userDetails;
            userService.changePassword(user.getId(), request);
            return ResponseEntity.ok(Map.of("message", "Contraseña actualizada con éxito"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}