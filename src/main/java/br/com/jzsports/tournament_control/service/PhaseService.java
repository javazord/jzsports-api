package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.dto.phase.PhaseDTO;
import br.com.jzsports.tournament_control.model.e.EMatchStatus;
import br.com.jzsports.tournament_control.model.e.ETypePhase;
import br.com.jzsports.tournament_control.model.entity.*;
import br.com.jzsports.tournament_control.model.mapper.PhaseMapper;
import br.com.jzsports.tournament_control.repository.ChampionshipRepository;
import br.com.jzsports.tournament_control.repository.PhaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PhaseService {

    private final PhaseRepository phaseRepository;
    private final ChampionshipRepository championshipRepository;
    private final PhaseMapper phaseMapper;
    private final MatchService matchService;

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

        List<Team> winnersList = currentPhaseEntity.getMatches().stream()
                .map(match -> {
                    if (match.getStatus() == EMatchStatus.CANCELLED) {
                        return matchService.getWinnerFromCancellation(match, match.getCancellingTeam());
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

        Phase nextPhaseEntity = new Phase();
        nextPhaseEntity.setChampionship(championship);
        nextPhaseEntity.setPhaseType(nextPhaseType);

        phaseRepository.save(nextPhaseEntity);

        matchService.createMatchesForPhase(nextPhaseEntity, new ArrayList<>(winnersList));
    }

    public PhaseDTO findByChampionshipId(Long id) {
        Phase phase = phaseRepository.findByChampionship_Id(id);
        return phaseMapper.toDto(phase);
    }
}
