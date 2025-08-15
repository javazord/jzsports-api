package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.dto.PhaseDTO;
import br.com.jzsports.tournament_control.model.e.ETypePhase;
import br.com.jzsports.tournament_control.model.entity.Championship;
import br.com.jzsports.tournament_control.model.entity.Match;
import br.com.jzsports.tournament_control.model.entity.Phase;
import br.com.jzsports.tournament_control.model.entity.Team;
import br.com.jzsports.tournament_control.model.mapper.PhaseMapper;
import br.com.jzsports.tournament_control.repository.ChampionshipRepository;
import br.com.jzsports.tournament_control.repository.PhaseRepository;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public PhaseDTO save(Phase phase) {
        return null;
    }

    public void generateNextPhase(Long championshipId, ETypePhase currentPhase) {
        Championship championship = championshipRepository.findById(championshipId)
                .orElseThrow(() -> new RuntimeException("Championship not found"));

        // Busca a fase atual
        Phase currentPhaseEntity = phaseRepository.findByChampionship_IdAndPhase(championshipId, currentPhase)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Current phase not found"));

        // Verifica se todos os jogos da fase atual têm vencedor
        boolean allFinished = currentPhaseEntity.getMatchesList()
                .stream()
                .allMatch(match -> matchService.getWinner(match) != null);

        if (!allFinished) {
            throw new RuntimeException("Not all matches are finished for " + currentPhase);
        }

        // Pega os vencedores da fase atual
        List<Team> winnersList = currentPhaseEntity.getMatchesList()
                .stream()
                .map(matchService::getWinner)
                .toList();

        ETypePhase nextPhaseType = currentPhase.getNext();
        if (nextPhaseType == null) {
            throw new RuntimeException("No next phase after " + currentPhase);
        }

        // Cria a nova fase
        Phase nextPhaseEntity = new Phase();
        nextPhaseEntity.setChampionship(championship);
        nextPhaseEntity.setPhase(nextPhaseType);
        nextPhaseEntity.setMatchesList(new ArrayList<>());

        phaseRepository.save(nextPhaseEntity);

        // Cria as partidas da próxima fase delegando ao MatchService
        List<Match> newMatches = matchService.createMatchesForPhase(nextPhaseEntity, winnersList);
        nextPhaseEntity.setMatchesList(newMatches);
    }

    // Método auxiliar para determinar o vencedor de uma partida
    private Team matchWinner(Match match) {
        if (match.getScoreTeam1() != null && match.getScoreTeam2() != null) {
            if (match.getScoreTeam1() > match.getScoreTeam2()) {
                return match.getTeamOne();
            } else if (match.getScoreTeam2() > match.getScoreTeam1()) {
                return match.getTeamTwo();
            }
        }
        return null; // empate ou partida não concluída
    }


    public PhaseDTO findById(Long id) {
        Phase phase = phaseRepository.findById(id).orElseThrow(() -> new RuntimeException("Phase not found"));
        return phaseMapper.toDto(phase);
    }

}
