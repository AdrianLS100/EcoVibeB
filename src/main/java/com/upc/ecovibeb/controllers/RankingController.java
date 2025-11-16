package com.upc.ecovibeb.controllers;

import com.upc.ecovibeb.dtos.RankingDTO;
import com.upc.ecovibeb.interfaces.IRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
public class RankingController {

    @Autowired
    private IRankingService rankingService;

    @GetMapping("/ranking")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<RankingDTO>> getRanking(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(rankingService.getRankingPersonal(page, size));
    }
}