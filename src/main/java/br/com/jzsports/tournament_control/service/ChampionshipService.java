package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.dto.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.entity.Championship;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.repository.ChampionshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChampionshipService {

    private ChampionshipRepository championshipRepository;

    public Championship updateChampionship(Long championshipId, Player loggedPlayer, ChampionshipDTO dto) {
        Championship championship = championshipRepository.findById(championshipId)
                .orElseThrow(() -> new RuntimeException("Championship not found"));

        if (!championship.getCreatedBy().getId().equals(loggedPlayer.getId())) {
            throw new RuntimeException("You do not have permission to update this championship");
        }

        championship.setName(dto.getName());
        championship.setType(dto.getType());
        championship.setStartDate(dto.getStartDate());
        championship.setEndDate(dto.getEndDate());

        return championshipRepository.save(championship);
    }

    public List<Championship> getChampionshipsByPlayer(Long playerId) {
        return championshipRepository.findByCreatedBy_Id(playerId);
    }

}
