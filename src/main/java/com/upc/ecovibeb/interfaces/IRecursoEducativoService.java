package com.upc.ecovibeb.interfaces;

import com.upc.ecovibeb.dtos.RecursoEducativoDTO;
import com.upc.ecovibeb.entities.RecursoEducativo.Tipo;
import org.springframework.data.domain.Page;

public interface IRecursoEducativoService {
    Page<RecursoEducativoDTO> listar(Tipo tipo, String q, int page, int size);
}