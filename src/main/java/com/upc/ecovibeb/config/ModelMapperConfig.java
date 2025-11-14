package com.upc.ecovibeb.config;

import com.upc.ecovibeb.dtos.ActividadesDiariasDTO;
import com.upc.ecovibeb.dtos.EnergiaDTO;
import com.upc.ecovibeb.dtos.ResiduoDTO;
import com.upc.ecovibeb.dtos.TransporteDTO;
import com.upc.ecovibeb.entities.ActividadesDiarias;
import com.upc.ecovibeb.entities.Energia;
import com.upc.ecovibeb.entities.Residuo;
import com.upc.ecovibeb.entities.Transporte;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper m = new ModelMapper();
        m.getConfiguration().setSkipNullEnabled(true);


        m.createTypeMap(ActividadesDiarias.class, ActividadesDiariasDTO.class)
                .addMappings(mp -> mp.map(src -> src.getUsuario().getId(), ActividadesDiariasDTO::setUsuarioId));

        m.createTypeMap(Transporte.class, TransporteDTO.class)
                .addMappings(mp -> mp.map(src -> src.getActividad().getId(), TransporteDTO::setActividadId));

        m.createTypeMap(Energia.class, EnergiaDTO.class)
                .addMappings(mp -> mp.map(src -> src.getActividad().getId(), EnergiaDTO::setActividadId));

        m.createTypeMap(Residuo.class, ResiduoDTO.class)
                .addMappings(mp -> mp.map(src -> src.getActividad().getId(), ResiduoDTO::setActividadId));

        return m;
    }
}