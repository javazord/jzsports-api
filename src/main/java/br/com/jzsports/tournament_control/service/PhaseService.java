package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.dto.PhaseDTO;
import br.com.jzsports.tournament_control.model.e.EMatchStatus;
import br.com.jzsports.tournament_control.model.e.ETypePhase;
import br.com.jzsports.tournament_control.model.entity.Championship;
import br.com.jzsports.tournament_control.model.entity.Match;
import br.com.jzsports.tournament_control.model.entity.Phase;
import br.com.jzsports.tournament_control.model.entity.Team;
import br.com.jzsports.tournament_control.model.mapper.PhaseMapper;
import br.com.jzsports.tournament_control.repository.ChampionshipRepository;
import br.com.jzsports.tournament_control.repository.PhaseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PhaseService {

    private final PhaseRepository phaseRepository;
    private final ChampionshipRepository championshipRepository;
    private final PhaseMapper phaseMapper;
    private final MatchService matchService;

    public PhaseService(PhaseRepository phaseRepository, PhaseMapper phaseMapper, ChampionshipRepository championshipRepository, MatchService matchService) {
        this.phaseRepository = phaseRepository;
        this.phaseMapper = phaseMapper;
        this.championshipRepository = championshipRepository;
        this.matchService = matchService;
    }

    public void generateNextPhase(Long championshipId, ETypePhase currentPhase) {
        Championship championship = championshipRepository.findById(championshipId)
                .orElseThrow(() -> new RuntimeException("Championship not found"));

        Phase currentPhaseEntity = phaseRepository.findByChampionship_IdAndPhase(championshipId, currentPhase)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Current phase not found"));

        boolean allFinishedOrCancelled = currentPhaseEntity.getMatchesList()
                .stream()
                .allMatch(match ->
                        match.getStatus() == EMatchStatus.FINISHED ||
                                match.getStatus() == EMatchStatus.CANCELLED
                );

        if (!allFinishedOrCancelled) {
            throw new RuntimeException("Not all matches are finished or cancelled for " + currentPhase);
        }

        List<Team> winnersList = currentPhaseEntity.getMatchesList().stream()
                .map(match -> {
                    if (match.getStatus() == EMatchStatus.CANCELLED) {
                        return matchService.getWinnerFromCancellation(match);
                    }
                    return matchService.getWinner(match);
                })
                .filter(Objects::nonNull)
                .toList();

        // 🚀 Agora a próxima fase depende da quantidade de times classificados
        ETypePhase nextPhaseType = ETypePhase.fromTeamCount(winnersList.size());
        if (nextPhaseType == null) {
            throw new RuntimeException("No valid next phase for " + winnersList.size() + " teams");
        }

        Phase nextPhaseEntity = new Phase();
        nextPhaseEntity.setChampionship(championship);
        nextPhaseEntity.setPhase(nextPhaseType);
        nextPhaseEntity.setMatchesList(new ArrayList<>());

        phaseRepository.save(nextPhaseEntity);

        // MatchService já trata bye automático e vencedor
        List<Match> newMatches = matchService.createMatchesForPhase(nextPhaseEntity, new ArrayList<>(winnersList));
        nextPhaseEntity.setMatchesList(newMatches);

        phaseRepository.save(nextPhaseEntity);
    }

    public PhaseDTO findById(Long id) {
        Phase phase = phaseRepository.findById(id).orElseThrow(() -> new RuntimeException("Phase not found"));
        return phaseMapper.toDto(phase);
    }

}
