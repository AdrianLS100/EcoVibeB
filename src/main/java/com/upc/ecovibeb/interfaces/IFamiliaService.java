package com.upc.ecovibeb.interfaces;

import com.upc.ecovibeb.dtos.FamiliaCrearRequest;
import com.upc.ecovibeb.dtos.FamiliaDTO;
import com.upc.ecovibeb.dtos.FamiliaDashboardDTO;
import com.upc.ecovibeb.dtos.FamiliaUnirseRequest;

public interface IFamiliaService {
    FamiliaDTO crearFamilia(FamiliaCrearRequest request, Long usuarioId);
    FamiliaDTO unirseAFamilia(FamiliaUnirseRequest request, Long usuarioId);
    FamiliaDashboardDTO getDashboardFamiliar(Long usuarioId);
}