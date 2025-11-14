package com.upc.ecovibeb.dtos;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CalculadoraPersonalDTO {

    private BigDecimal horasBusSemana;
    private BigDecimal horasTrenSemana;
    private BigDecimal horasMetropolitanoSemana;
    private BigDecimal horasAutoSemana;

    private BigDecimal kwhMes;
    private Integer balonesGlp10kgMes;

    private Integer diasCarnePorSemana;

    private Integer bolsas5L;
    private Integer bolsas10L;
    private Integer bolsas20L;
    private List<String> tiposReciclaje;

    private BigDecimal totalTransporteTon;
    private BigDecimal totalEnergiaTon;
    private BigDecimal totalAlimentacionTon;
    private BigDecimal totalResiduosTon;

    private BigDecimal totalKgCO2e;
}