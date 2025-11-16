package com.upc.ecovibeb.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificacionDTO {
    private Long id;
    private String mensaje;
    private boolean leido;
    private LocalDateTime fechaCreacion;
    private String linkRuta;
}