package com.upc.ecovibeb.interfaces;

import com.upc.ecovibeb.dtos.CalculadoraPersonalDTO;

public interface ICalculadoraService {
    CalculadoraPersonalDTO calcularHuellaPersonal(CalculadoraPersonalDTO request);
    Double calcularHuellaFamiliar(Long familiaId);
}