package com.upc.ecovibeb.services;

import com.upc.ecovibeb.dtos.RecomendacionDTO;
import com.upc.ecovibeb.security.entities.User;
import com.upc.ecovibeb.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RecomendacionService {

    @Autowired
    private UserRepository userRepo;

    public List<RecomendacionDTO> generarRecomendaciones(Long usuarioId) {
        User user = userRepo.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        List<RecomendacionDTO> recomendaciones = new ArrayList<>();
        boolean esFamilia = (user.getFamilia() != null);

        if (user.getHuellaTotalKgCO2e() == null) {
            recomendaciones.add(new RecomendacionDTO("General",
                    "Aún no has calculado tu huella de carbono. ¡Usa la calculadora para obtener consejos personalizados!",
                    "fa-calculator", "ALTO"));
            return recomendaciones;
        }

        BigDecimal huellaTransporte = user.getHuellaTransporte();
        if (huellaTransporte != null && huellaTransporte.compareTo(new BigDecimal("1000")) > 0) {
            if (esFamilia) {
                recomendaciones.add(new RecomendacionDTO("Transporte Familiar",
                        "La huella de transporte de tu familia es alta. Consideren compartir el auto (carpooling) para llevar a los niños o hacer compras grandes.",
                        "fa-car-side", "ALTO"));
            } else {
                recomendaciones.add(new RecomendacionDTO("Transporte",
                        "Tu huella de transporte es significativa. Intenta usar el transporte público o bicicleta al menos 2 días a la semana.",
                        "fa-bicycle", "ALTO"));
            }
        } else {
            recomendaciones.add(new RecomendacionDTO("Transporte",
                    "¡Vas bien en transporte! Mantén tus hábitos de movilidad sostenible.",
                    "fa-thumbs-up", "BAJO"));
        }

        BigDecimal huellaEnergia = user.getHuellaEnergia();
        if (huellaEnergia != null && huellaEnergia.compareTo(new BigDecimal("800")) > 0) {
            if (esFamilia) {
                recomendaciones.add(new RecomendacionDTO("Energía en Casa",
                        "El consumo eléctrico es alto. Organicen una 'hora sin tecnología' en familia o cambien a bombillas LED en toda la casa.",
                        "fa-lightbulb", "MEDIO"));
            } else {
                recomendaciones.add(new RecomendacionDTO("Energía",
                        "No olvides desconectar tus dispositivos cuando no los uses. El 'consumo vampiro' suma mucho a tu huella.",
                        "fa-plug", "MEDIO"));
            }
        }

        BigDecimal huellaAlim = user.getHuellaAlimentacion();
        if (huellaAlim != null && huellaAlim.compareTo(new BigDecimal("1500")) > 0) {
            recomendaciones.add(new RecomendacionDTO("Alimentación",
                    esFamilia ? "Intenten implementar los 'Lunes sin Carne' en las cenas familiares." : "Reducir el consumo de carnes rojas puede bajar drásticamente tu huella personal.",
                    "fa-utensils", "MEDIO"));
        }

        BigDecimal huellaResiduos = user.getHuellaResiduos();
        if (huellaResiduos != null && huellaResiduos.compareTo(new BigDecimal("200")) > 0) {
            recomendaciones.add(new RecomendacionDTO("Residuos",
                    "Parece que generas muchos residuos. ¿Ya empezaste a separar plásticos y cartón?",
                    "fa-recycle", "ALTO"));
        }

        return recomendaciones;
    }
}