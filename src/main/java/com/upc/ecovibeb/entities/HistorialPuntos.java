package com.upc.ecovibeb.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_puntos")
@Getter
@Setter
public class HistorialPuntos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(nullable = false, length = 60)
    private String codigoAccion;

    @Column(nullable = false)
    private Integer puntos;

    @Column(length = 200)
    private String detalle;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();
}