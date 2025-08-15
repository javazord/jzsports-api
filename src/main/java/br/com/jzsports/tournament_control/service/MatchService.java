package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.entity.Match;
import br.com.jzsports.tournament_control.model.entity.Phase;
import br.com.jzsports.tournament_control.model.entity.Team;
import br.com.jzsports.tournament_control.model.mapper.MatchMapper;
import br.com.jzsports.tournament_control.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    public MatchService(MatchRepository matchRepository, MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
    }

    // Cria partidas para uma fase a partir de uma lista de times
    public List<Match> createMatchesForPhase(Phase phase, List<Team> teams) {
        List<Match> matches = new ArrayList<>();

        for (int i = 0; i < teams.size(); i += 2) {
            Match match = new Match();
            match.setPhase(phase);
            match.setChampionship(phase.getChampionship());
            match.setTeamOne(teams.get(i));
            if (i + 1 < teams.size()) {
                match.setTeamTwo(teams.get(i + 1));
            }
            matches.add(match);
        }

        return matchRepository.saveAll(matches);
    }

    // Determina o vencedor de uma partida
    public Team getWinner(Match match) {
        if (match.getScoreTeam1() != null && match.getScoreTeam2() != null) {
            if (match.getScoreTeam1() > match.getScoreTeam2()) return match.getTeamOne();
            if (match.getScoreTeam2() > match.getScoreTeam1()) return match.getTeamTwo();
        }
        return null; // empate ou partida não concluída
    }

}
