package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.entity.Championship;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.mapper.ChampionshipMapper;
import br.com.jzsports.tournament_control.repository.ChampionshipRepository;
import br.com.jzsports.tournament_control.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChampionshipService {

    private final ChampionshipRepository championshipRepository;
    private final ChampionshipMapper championshipMapper;
    private final PlayerRepository playerRepository;

    public ChampionshipService(ChampionshipRepository championshipRepository, ChampionshipMapper championshipMapper, PlayerRepository playerRepository) {
        this.championshipRepository = championshipRepository;
        this.championshipMapper = championshipMapper;
        this.playerRepository = playerRepository;
    }

    public ChampionshipDTO save(Championship championship) {
        Player player = playerRepository.findById(championship.getCreatedBy().getId()).orElseThrow(() -> new EntityNotFoundException("Player not found"));
        championship.setCreatedBy(player);
        return championshipMapper.toDto(championship);
    }

    public ChampionshipDTO updateChampionship(Long championshipId, Player loggedPlayer, ChampionshipDTO dto) {
        Championship championship = championshipRepository.findById(championshipId)
                .orElseThrow(() -> new RuntimeException("Championship not found"));

        if (!championship.getCreatedBy().getId().equals(loggedPlayer.getId())) {
            throw new RuntimeException("You do not have permission to update this championship");
        }

        championship.setChampionshipName(dto.getName());
        championship.setChampionshipType(dto.getType());
        championship.setChampionshipStatus(dto.getStatus());
        championship.setStartDate(dto.getStartDate());
        championship.setEndDate(dto.getEndDate());
        championshipRepository.save(championship);
        return championshipMapper.toDto(championship);
    }

    public List<ChampionshipDTO> getChampionshipsByPlayer(Long playerId) {
        return championshipRepository.findByCreatedBy_Id(playerId);
    }

    public Championship getChampionshipById(Long championshipId) {
        return championshipRepository.findById(championshipId).orElseThrow(() -> new EntityNotFoundException("Championship not found with id " + championshipId));
    }

}
