package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.e.EChampionshipStatus;
import br.com.jzsports.tournament_control.model.e.ETypeChampionship;
import br.com.jzsports.tournament_control.model.e.ETypePhase;
import br.com.jzsports.tournament_control.model.entity.Championship;
import br.com.jzsports.tournament_control.model.entity.Match;
import br.com.jzsports.tournament_control.model.entity.Phase;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.mapper.ChampionshipMapper;
import br.com.jzsports.tournament_control.repository.ChampionshipRepository;
import br.com.jzsports.tournament_control.repository.PhaseRepository;
import br.com.jzsports.tournament_control.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChampionshipService {

    private final ChampionshipRepository championshipRepository;
    private final ChampionshipMapper championshipMapper;
    private final PlayerRepository playerRepository;
    private final PhaseRepository phaseRepository;
    private final MatchService matchService;

    public ChampionshipService(ChampionshipRepository championshipRepository, ChampionshipMapper championshipMapper, PlayerRepository playerRepository, PhaseRepository phaseRepository, MatchService matchService) {
        this.championshipRepository = championshipRepository;
        this.championshipMapper = championshipMapper;
        this.playerRepository = playerRepository;
        this.phaseRepository = phaseRepository;
        this.matchService = matchService;
    }

    @Transactional
    public ChampionshipDTO save(Championship championship) {
        // 🔹 Valida player criador
        Player player = playerRepository.findById(championship.getCreatedBy().getId())
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));
        championship.setCreatedBy(player);
        championship.setChampionshipStatus(EChampionshipStatus.IN_PROGRESS);

        // 🔹 Salva campeonato
        Championship savedChampionship = championshipRepository.save(championship);

        // 🔹 Determina a primeira fase com base na quantidade de times
        int teamCount = savedChampionship.getTeamsList().size();
        ETypePhase firstPhaseType = ETypePhase.fromTeamCount(teamCount);
        if (firstPhaseType == null) {
            throw new RuntimeException("Invalid team count: " + teamCount);
        }

        // 🔹 Cria fase inicial
        Phase firstPhase = new Phase();
        firstPhase.setChampionship(savedChampionship);
        firstPhase.setPhaseType(firstPhaseType);

        phaseRepository.save(firstPhase);

        // 🔹 Cria as partidas da fase
        List<Match> matches = matchService.createMatchesForPhase(firstPhase, new ArrayList<>(savedChampionship.getTeamsList()));
        firstPhase.setMatchesList(matches);

        phaseRepository.save(firstPhase);

        // 🔹 Retorna DTO com campeonato completo (já com a primeira fase + partidas)
        return championshipMapper.toDto(savedChampionship);
    }

    public ChampionshipDTO updateChampionship(Long championshipId, Player loggedPlayer, ChampionshipDTO dto) {
        Championship championship = championshipRepository.findById(championshipId)
                .orElseThrow(() -> new RuntimeException("Championship not found"));

        if (!championship.getCreatedBy().getId().equals(loggedPlayer.getId())) {
            throw new RuntimeException("You do not have permission to update this championship");
        }

        championship.setChampionshipName(dto.getChampionshipName());
        championship.setChampionshipType(ETypeChampionship.valueOf(dto.getChampionshipTypeDescription()));
        championship.setChampionshipStatus(EChampionshipStatus.valueOf(dto.getChampionshipStatusDescription()));
        championship.setStartDate(dto.getStartDate());
        championship.setEndDate(dto.getEndDate());
        championshipRepository.save(championship);
        return championshipMapper.toDto(championship);
    }

    public List<ChampionshipDTO> getChampionshipsByCreatedBy(Long playerId) {
        return championshipRepository.findByCreatedBy_Id(playerId);
    }

    public List<Championship> getByPlayerIncluded(Long playerId) {
        return championshipRepository.findByPlayerIncluded(playerId);
    }

    public Championship getChampionshipById(Long championshipId) {
        return championshipRepository.findById(championshipId).orElseThrow(() -> new EntityNotFoundException("Championship not found with id " + championshipId));
    }

}
