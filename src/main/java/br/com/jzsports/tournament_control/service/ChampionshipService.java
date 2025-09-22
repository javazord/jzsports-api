package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.e.EChampionshipStatus;
import br.com.jzsports.tournament_control.model.e.EMatchStatus;
import br.com.jzsports.tournament_control.model.e.ETypeChampionship;
import br.com.jzsports.tournament_control.model.e.ETypePhase;
import br.com.jzsports.tournament_control.model.entity.*;
import br.com.jzsports.tournament_control.model.mapper.ChampionshipMapper;
import br.com.jzsports.tournament_control.repository.ChampionshipRepository;
import br.com.jzsports.tournament_control.repository.PhaseRepository;
import br.com.jzsports.tournament_control.repository.PlayerRepository;
import br.com.jzsports.tournament_control.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChampionshipService {

    private final ChampionshipRepository championshipRepository;
    private final ChampionshipMapper championshipMapper;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final PhaseRepository phaseRepository;
    private final MatchService matchService;

    // ==================== SAVE CHAMPIONSHIP ====================
    @Transactional
    public ChampionshipDTO save(ChampionshipDTO dto) {
        // 🔹 Valida player criador
        Player player = playerRepository.findById(dto.getCreatedByPlayerId())
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));

        dto.setCreatedByPlayerId(player.getId());
        dto.setChampionshipStatus(String.valueOf(EChampionshipStatus.IN_PROGRESS));

        // 🔹 Mapeia campeonato, mas ignora os times (mapper já configurado com ignore)
        Championship championship = championshipMapper.toEntity(dto);
        championship.setCreatedBy(player);

        // 🔹 Busca os teams pelos IDs vindos no DTO
        List<Team> teams = teamRepository.findAllById(dto.getTeamIds());

        // 🔹 Monta os participantes
        List<ChampionshipParticipant> participants = teams.stream()
                .map(team -> new ChampionshipParticipant(null, championship, team, player))
                .toList();

        championship.setParticipants(participants);

        // 🔹 Salva campeonato (cascata salva participantes)
        Championship savedChampionship = championshipRepository.save(championship);

        // 🔹 Cria fase inicial com base na quantidade de times
        int teamCount = teams.size();
        ETypePhase firstPhaseType = ETypePhase.fromTeamCount(teamCount);
        if (firstPhaseType == null) {
            throw new RuntimeException("Invalid team count: " + teamCount);
        }

        Phase firstPhase = new Phase();
        firstPhase.setChampionship(savedChampionship);
        firstPhase.setPhaseType(firstPhaseType);
        phaseRepository.save(firstPhase);

        // 🔹 Cria as partidas da fase inicial
        List<Match> matches = matchService.createMatchesForPhase(firstPhase, new ArrayList<>(teams));
        firstPhase.setMatches(matches);
        phaseRepository.save(firstPhase);

        return championshipMapper.toDto(savedChampionship);
    }

    // ==================== UPDATE CHAMPIONSHIP ====================
    @Transactional
    public ChampionshipDTO updateChampionship(Long championshipId, Long loggedPlayerId, ChampionshipDTO dto) {
        Championship championship = championshipRepository.findById(championshipId)
                .orElseThrow(() -> new RuntimeException("Championship not found"));

        if (!championship.getCreatedBy().getId().equals(loggedPlayerId)) {
            throw new RuntimeException("You do not have permission to update this championship");
        }

        championship.setChampionshipName(dto.getChampionshipName());
        championship.setChampionshipType(ETypeChampionship.valueOf(dto.getChampionshipType()));
        championship.setChampionshipStatus(EChampionshipStatus.valueOf(dto.getChampionshipStatus()));
        championship.setStartDate(dto.getStartDate());
        championship.setEndDate(dto.getEndDate());
        championshipRepository.save(championship);

        return championshipMapper.toDto(championship);
    }

    // ==================== GET CHAMPIONSHIPS BY CREATOR ====================
    public List<ChampionshipDTO> getChampionshipsByCreatedBy(Long playerId) {
        return championshipRepository.findByCreatedBy_Id(playerId).stream()
                .map(championshipMapper::toDto)
                .toList();
    }

    // ==================== GET CHAMPIONSHIPS BY PLAYER INCLUDED ====================
    public List<ChampionshipDTO> getByPlayerIncluded(Long playerId) {
        List<Championship> championships = championshipRepository.findDistinctByParticipants_Team_Memberships_Player_Id(playerId);
        return championshipMapper.toDtoList(championships);
    }

    // ==================== GET CHAMPIONSHIP BY ID ====================
    public Championship getChampionshipById(Long championshipId) {
        return championshipRepository.findById(championshipId)
                .orElseThrow(() -> new EntityNotFoundException("Championship not found with id " + championshipId));
    }

    // ==================== GENERATE NEXT PHASE ====================
    @Transactional
    public void generateNextPhase(Long championshipId, ETypePhase currentPhase) {
        Championship championship = championshipRepository.findById(championshipId)
                .orElseThrow(() -> new RuntimeException("Championship not found"));

        Phase currentPhaseEntity = phaseRepository.findByChampionship_IdAndPhaseType(championshipId, currentPhase)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Current phase not found"));

        boolean allFinishedOrCancelled = currentPhaseEntity.getMatches()
                .stream()
                .allMatch(match -> match.getStatus() == EMatchStatus.FINISHED
                        || match.getStatus() == EMatchStatus.CANCELLED);

        if (!allFinishedOrCancelled) {
            throw new RuntimeException("Not all matches are finished or cancelled for " + currentPhase);
        }

        // Pega vencedores da fase atual
        List<Team> winnersList = currentPhaseEntity.getMatches().stream()
                .map(match -> {
                    if (match.getStatus() == EMatchStatus.CANCELLED) {
                        MatchParticipant cancellingParticipant = match.getParticipants().stream()
                                .filter(MatchParticipant::isCancelled)
                                .findFirst()
                                .orElse(null);
                        Team cancellingTeam = cancellingParticipant != null ? cancellingParticipant.getTeam() : null;
                        return matchService.getWinnerFromCancellation(match, cancellingTeam);
                    }
                    return matchService.getWinner(match);
                })
                .filter(Objects::nonNull)
                .toList();

        // Determina próxima fase
        ETypePhase nextPhaseType = ETypePhase.fromTeamCount(winnersList.size());
        if (nextPhaseType == null) {
            throw new RuntimeException("No valid next phase for " + winnersList.size() + " teams");
        }

        // Cria próxima fase
        Phase nextPhaseEntity = new Phase();
        nextPhaseEntity.setChampionship(championship);
        nextPhaseEntity.setPhaseType(nextPhaseType);
        phaseRepository.save(nextPhaseEntity);

        // Cria partidas para a próxima fase
        List<Match> nextMatches = matchService.createMatchesForPhase(nextPhaseEntity, new ArrayList<>(winnersList));
        nextPhaseEntity.setMatches(nextMatches);
        phaseRepository.save(nextPhaseEntity);
    }
}
