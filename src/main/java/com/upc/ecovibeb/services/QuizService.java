package com.upc.ecovibeb.services;

import com.upc.ecovibeb.entities.HistorialPuntos;
import com.upc.ecovibeb.entities.Pregunta;
import com.upc.ecovibeb.repositories.HistorialPuntosRepository;
import com.upc.ecovibeb.repositories.PreguntaRepository;
import com.upc.ecovibeb.security.entities.User;
import com.upc.ecovibeb.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QuizService {

    @Autowired private PreguntaRepository preguntaRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private HistorialPuntosRepository historialRepo;
    @Autowired private NotificacionService notificacionService;

    public List<Pregunta> obtenerQuizAleatorio() {
        return preguntaRepo.findRandomQuestions();
    }

    @Transactional
    public Integer registrarVictoria(Long usuarioId, Integer puntosGanados) {
        User user = userRepo.findById(usuarioId).orElseThrow();

        HistorialPuntos ganancia = new HistorialPuntos();
        ganancia.setUsuario(user);
        ganancia.setCodigoAccion("QUIZ_WIN");
        ganancia.setPuntos(puntosGanados);
        ganancia.setDetalle("Victoria en Quiz Educativo");
        historialRepo.save(ganancia);

        notificacionService.crearNotificacion(user, "¡Ganaste " + puntosGanados + " puntos aprendiendo sobre sostenibilidad!", "/tienda");

        return puntosGanados;
    }
}