package com.upc.ecovibeb.services;

import com.upc.ecovibeb.dtos.FamiliaRankingDTO;
import com.upc.ecovibeb.dtos.RankingDTO;
import com.upc.ecovibeb.repositories.FamiliaRepository;
import com.upc.ecovibeb.security.entities.User;
import com.upc.ecovibeb.interfaces.IRankingService;
import com.upc.ecovibeb.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RankingService implements IRankingService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private FamiliaRepository familiaRepo;

    @Override
    public List<RankingDTO> getRankingPersonal(int page, int size) {
        Page<User> userPage = userRepo.findUsersForRanking(PageRequest.of(page, size));

        List<RankingDTO> rankingList = new ArrayList<>();
        long rankInicial = (long) page * size + 1;

        for (User user : userPage.getContent()) {
            rankingList.add(new RankingDTO(
                    rankInicial++,
                    user.getUsername(),
                    user.getHuellaTotalKgCO2e(),
                    user.getHuellaTransporte(),
                    user.getHuellaEnergia(),
                    user.getHuellaAlimentacion(),
                    user.getHuellaResiduos()
            ));
        }
        return rankingList;
    }

    public List<FamiliaRankingDTO> getRankingFamiliar() {
        List<FamiliaRankingDTO> ranking = familiaRepo.obtenerRankingFamilias();

        // Asignamos el número de puesto (1, 2, 3...)
        long rank = 1;
        for (FamiliaRankingDTO dto : ranking) {
            dto.setRank(rank++);
        }

        // Devolvemos solo el Top 10
        if (ranking.size() > 10) {
            return ranking.subList(0, 10);
        }
        return ranking;
    }
}