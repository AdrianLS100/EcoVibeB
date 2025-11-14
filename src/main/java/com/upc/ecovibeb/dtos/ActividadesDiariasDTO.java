package com.upc.ecovibeb.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ActividadesDiariasDTO {

    private Long id;
    private LocalDate fecha;
    private Long usuarioId;
    private String descripcion;

}