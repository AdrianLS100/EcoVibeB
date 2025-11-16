package com.upc.ecovibeb.interfaces;

import com.upc.ecovibeb.dtos.RankingDTO;
import java.util.List;

public interface IRankingService {
    List<RankingDTO> getRankingPersonal(int page, int size);
}