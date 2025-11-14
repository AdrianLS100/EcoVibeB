package com.upc.ecovibeb.services;

import com.upc.ecovibeb.dtos.ActividadesDiariasDTO;
import com.upc.ecovibeb.entities.ActividadesDiarias;
import com.upc.ecovibeb.entities.User; // <-- Asegúrate de importar tu entidad User
import com.upc.ecovibeb.interfaces.IActividadesDiariasService;
import com.upc.ecovibeb.repositories.ActividadesDiariasRepository;
import com.upc.ecovibeb.repositories.UserRepository; // <-- Asegúrate de importar tu UserRepository
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException; // <-- Importar
import java.util.Optional;

@Service
public class ActividadesDiariasService implements IActividadesDiariasService {

    @Autowired
    private ActividadesDiariasRepository actividadesRepo;
    @Autowired
    private UserRepository userRepo; // (Necesario para buscar el usuario)
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ActividadesDiariasDTO crearActividad(Long usuarioId, LocalDate fecha, String descripcion) {

        Optional<ActividadesDiarias> existente = actividadesRepo.findByUsuarioIdAndFecha(usuarioId, fecha);

        if (existente.isPresent()) {
            throw new IllegalArgumentException("Ya existe una actividad registrada para esta fecha.");
        }

        ActividadesDiarias nuevaActividad = new ActividadesDiarias();

        User usuario = userRepo.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Usuario no existe: " + usuarioId));

        nuevaActividad.setUsuario(usuario);
        nuevaActividad.setFecha(fecha);
        nuevaActividad.setDescripcion(descripcion);

        ActividadesDiarias actividadGuardada = actividadesRepo.save(nuevaActividad);

        return modelMapper.map(actividadGuardada, ActividadesDiariasDTO.class);
    }

    @Override
    public Optional<ActividadesDiariasDTO> obtenerPorId(Long actividadId) {
        return actividadesRepo.findById(actividadId)
                .map(entity -> modelMapper.map(entity, ActividadesDiariasDTO.class));
    }

    @Override
    public void eliminar(Long actividadId) {
        if (!actividadesRepo.existsById(actividadId)) {
            throw new NoSuchElementException("Actividad no encontrada con ID: " + actividadId);
        }
        actividadesRepo.deleteById(actividadId);
    }
}