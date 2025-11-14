package com.upc.ecovibeb.interfaces;

import com.upc.ecovibeb.dtos.CanjearRequest;
import com.upc.ecovibeb.dtos.EstadoGamificacionDTO;
import com.upc.ecovibeb.dtos.OtorgarPuntoRequest;
import com.upc.ecovibeb.dtos.RecompensaDTO;
import java.util.List;

public interface IGamificacionService {

    List<RecompensaDTO> listarRecompensas();

    EstadoGamificacionDTO getEstadoUsuario(Long usuarioId);

    EstadoGamificacionDTO canjearRecompensa(CanjearRequest request);

    EstadoGamificacionDTO analizarYOtorgarPuntos(Long actividadId, Long usuarioId);
}