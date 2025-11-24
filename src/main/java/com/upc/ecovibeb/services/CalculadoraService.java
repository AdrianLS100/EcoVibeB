package com.upc.ecovibeb.services;

import com.upc.ecovibeb.dtos.CalculadoraPersonalDTO;
import com.upc.ecovibeb.dtos.FactoresEmisionDTO;
import com.upc.ecovibeb.interfaces.ICalculadoraService;
import com.upc.ecovibeb.interfaces.IFactoresEmisionService;
import com.upc.ecovibeb.security.entities.User;
import com.upc.ecovibeb.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;

@Service
public class CalculadoraService implements ICalculadoraService {

    @Autowired
    private IFactoresEmisionService factoresService;

    @Autowired
    private UserRepository userRepo;

    private static final BigDecimal CERO = BigDecimal.ZERO;
    private static final BigDecimal SEMANAS_AL_ANIO = new BigDecimal(52);
    private static final BigDecimal MESES_AL_ANIO = new BigDecimal(12);
    private static final BigDecimal DIAS_AL_ANIO = new BigDecimal(365);
    private static final BigDecimal KG_POR_BALON_GLP = new BigDecimal(10);
    private static final BigDecimal KG_POR_BOLSA_10L = new BigDecimal("1.5"); // Estimación
    private static final BigDecimal KG_EN_TONELADA = new BigDecimal(1000);
    private static final BigDecimal VELOCIDAD_PROMEDIO_BUS = new BigDecimal(20);
    private static final BigDecimal VELOCIDAD_PROMEDIO_AUTO = new BigDecimal(30);

    @Override
    public CalculadoraPersonalDTO calcularHuellaPersonal(CalculadoraPersonalDTO req) {

        // --- ¡CAMBIO OBLIGATORIO (INICIO)! ---
        // 1. Validar que el usuarioId exista en la petición
        if (req.getUsuarioId() == null) {
            throw new IllegalArgumentException("El usuarioId no puede ser nulo para calcular la huella.");
        }
        // 2. Cargar la entidad User desde la base de datos
        User user = userRepo.findById(req.getUsuarioId())
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con ID: " + req.getUsuarioId()));
        // --- FIN DEL CAMBIO ---

        // 1. CALCULAR TRANSPORTE (kg CO2e / año)
        // (Tu lógica de cálculo está perfecta)
        BigDecimal totalTransporte = CERO
                .add(calcularEmision(req.getHorasBusSemana(), VELOCIDAD_PROMEDIO_BUS, SEMANAS_AL_ANIO, getFactor("transporte", "transporte publico", "km")))
                .add(calcularEmision(req.getHorasTrenSemana(), VELOCIDAD_PROMEDIO_BUS, SEMANAS_AL_ANIO, getFactor("transporte", "transporte publico", "km")))
                .add(calcularEmision(req.getHorasMetropolitanoSemana(), VELOCIDAD_PROMEDIO_BUS, SEMANAS_AL_ANIO, getFactor("transporte", "transporte publico", "km")))
                .add(calcularEmision(req.getHorasAutoSemana(), VELOCIDAD_PROMEDIO_AUTO, SEMANAS_AL_ANIO, getFactor("transporte", "auto", "km")));

        // 2. CALCULAR ENERGÍA (kg CO2e / año)
        BigDecimal kwhAnual = nz(req.getKwhMes()).multiply(MESES_AL_ANIO);
        BigDecimal glpAnual = new BigDecimal(nz(req.getBalonesGlp10kgMes()))
                .multiply(KG_POR_BALON_GLP)
                .multiply(MESES_AL_ANIO);
        BigDecimal totalEnergia = kwhAnual.multiply(getFactor("energia", "electricidad", "kWh"))
                .add(glpAnual.multiply(getFactor("energia", "glp", "kg")));

        // 3. CALCULAR ALIMENTACIÓN (kg CO2e / año)
        String tipoDieta = "vegana";
        int diasCarne = nz(req.getDiasCarnePorSemana());
        if (diasCarne >= 4) tipoDieta = "omnivora";
        else if (diasCarne > 0) tipoDieta = "vegetariana";
        BigDecimal totalAlimentacion = DIAS_AL_ANIO.multiply(getFactor("alimentacion", tipoDieta, "dia"));

        // 4. CALCULAR RESIDUOS (kg CO2e / año)
        BigDecimal kgBolsa5L = new BigDecimal(nz(req.getBolsas5L())).multiply(KG_POR_BOLSA_10L.divide(new BigDecimal(2)));
        BigDecimal kgBolsa10L = new BigDecimal(nz(req.getBolsas10L())).multiply(KG_POR_BOLSA_10L);
        BigDecimal kgBolsa20L = new BigDecimal(nz(req.getBolsas20L())).multiply(KG_POR_BOLSA_10L.multiply(new BigDecimal(2)));
        BigDecimal kgTotalResiduosAnual = (kgBolsa5L.add(kgBolsa10L).add(kgBolsa20L)).multiply(DIAS_AL_ANIO);
        BigDecimal factorReduccion = (req.getTiposReciclaje() != null && !req.getTiposReciclaje().isEmpty() && !req.getTiposReciclaje().contains("ninguno"))
                ? new BigDecimal("0.75")
                : BigDecimal.ONE;
        BigDecimal totalResiduos = kgTotalResiduosAnual.multiply(getFactor("residuos", "general", "kg")).multiply(factorReduccion);

        // 5. TOTALIZAR Y RELLENAR EL DTO DE RESPUESTA
        BigDecimal totalKgAnual = totalTransporte.add(totalEnergia).add(totalAlimentacion).add(totalResiduos);
        System.out.println("CÁLCULO FINALIZADO: " + totalKgAnual + " kg CO2e");
        req.setTotalKgCO2e(totalKgAnual.setScale(2, RoundingMode.HALF_UP));
        user.setHuellaTotalKgCO2e(totalKgAnual.setScale(2, RoundingMode.HALF_UP));
        user.setHuellaTransporte(totalTransporte.setScale(2, RoundingMode.HALF_UP));
        user.setHuellaEnergia(totalEnergia.setScale(2, RoundingMode.HALF_UP));
        user.setHuellaAlimentacion(totalAlimentacion.setScale(2, RoundingMode.HALF_UP));
        user.setHuellaResiduos(totalResiduos.setScale(2, RoundingMode.HALF_UP));
        userRepo.save(user);
        // --- FIN DEL CAMBIO ---

        return req;
    }

    // --- Métodos de Ayuda (Estos ya están bien) ---

    private BigDecimal getFactor(String cat, String sub, String unidad) {
        return factoresService.buscarVigente(cat, sub, unidad)
                .map(FactoresEmisionDTO::getFactorKgco2ePerUnidad)
                .orElseThrow(() -> new IllegalStateException(
                        String.format("¡Falta factor de emisión! Categoria: %s, Sub: %s, Unidad: %s", cat, sub, unidad)
                ));
    }

    private BigDecimal calcularEmision(BigDecimal horas, BigDecimal vel, BigDecimal periodo, BigDecimal factor) {
        if (horas == null || horas.equals(CERO)) return CERO;
        return horas.multiply(vel).multiply(periodo).multiply(factor);
    }

    private BigDecimal nz(BigDecimal val) {
        return (val == null) ? CERO : val;
    }
    private Integer nz(Integer val) {
        return (val == null) ? 0 : val;
    }
}