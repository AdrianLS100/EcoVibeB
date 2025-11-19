package com.upc.ecovibeb.dtos;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class InstitucionDTO {
    private Long id;
    private String nombre;
    private String tipo;
    private String codigoInvitacion;
    private int cantidadMiembros;
}