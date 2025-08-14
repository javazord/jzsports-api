package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.dto.PhaseDTO;
import br.com.jzsports.tournament_control.model.entity.Championship;
import br.com.jzsports.tournament_control.model.entity.Phase;
import br.com.jzsports.tournament_control.model.mapper.PhaseMapper;
import br.com.jzsports.tournament_control.repository.ChampionshipRepository;
import br.com.jzsports.tournament_control.repository.PhaseRepository;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.util.List;

@Service
public class PhaseService {

    private final PhaseRepository phaseRepository;
    private final ChampionshipRepository championshipRepository;
    private final PhaseMapper phaseMapper;

    public PhaseService(PhaseRepository phaseRepository, PhaseMapper phaseMapper, ChampionshipRepository championshipRepository) {
        this.phaseRepository = phaseRepository;
        this.phaseMapper = phaseMapper;
        this.championshipRepository = championshipRepository;
    }

    public PhaseDTO save(Phase phase) {

    }

    public void generateNextPhase(Long championshipId, ElementType currentPhase) {
        Championship championship = championshipRepository.findById(championshipId).orElseThrow(() -> new RuntimeException("Championship not found"));

        //verifica se todos os jogos da fase atual tem vencedor
        List<Phase> currentMatches = phaseRepository.findByCampionship_IdAndPhase(championshipId, currentPhase);

        boolean allFinished = currentMatches.stream().allMatch(match -> match.getWinner() != null);

        if (!allFinished) {
            throw new RuntimeException("Not all matches are finished for " + currentPhase);
        }

        

    }


}
