package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.e.EMatchStatus;
import br.com.jzsports.tournament_control.model.entity.Match;
import br.com.jzsports.tournament_control.model.entity.Phase;
import br.com.jzsports.tournament_control.model.entity.Team;
import br.com.jzsports.tournament_control.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    // Cria partidas para uma fase (inclui bye match se necessário)
    public List<Match> createMatchesForPhase(Phase phase, List<Team> teams) {
        List<Match> matches = new ArrayList<>();

        // Se número ímpar de times, cria bye automático
        if (teams.size() % 2 != 0) {
            Team byeTeam = teams.remove(teams.size() - 1);

            Match byeMatch = new Match();
            byeMatch.setPhase(phase);
            byeMatch.setChampionship(phase.getChampionship());
            byeMatch.setTeamOne(byeTeam);
            byeMatch.setStatus(EMatchStatus.FINISHED);
            byeMatch.setScoreTeamOne(0);
            byeMatch.setScoreTeamTwo(null);
            // Vencedor automático
            phase.setWinner(byeTeam); // opcional se quiser registrar na Phase
            matches.add(byeMatch);
        }

        // Cria os matches normais
        for (int i = 0; i < teams.size(); i += 2) {
            Match match = new Match();
            match.setPhase(phase);
            match.setChampionship(phase.getChampionship());
            match.setTeamOne(teams.get(i));
            if (i + 1 < teams.size()) {
                match.setTeamTwo(teams.get(i + 1));
            }
            match.setStatus(EMatchStatus.IN_PROGRESS); // início padrão
            matches.add(match);
        }

        return matchRepository.saveAll(matches);
    }

    // Determina o vencedor de uma partida finalizada
    public Team getWinner(Match match) {
        if (match.getStatus() != EMatchStatus.FINISHED) {
            return null;
        }
        if (match.getScoreTeamOne() != null && match.getScoreTeamTwo() != null) {
            if (match.getScoreTeamOne() > match.getScoreTeamTwo()) return match.getTeamOne();
            if (match.getScoreTeamTwo() > match.getScoreTeamOne()) return match.getTeamTwo();
        }
        return match.getTeamOne(); // no caso de bye, teamOne é o vencedor
    }

    // Determina vencedor no caso de cancelamento
    public Team getWinnerFromCancellation(Match match) {
        if (match.getStatus() == EMatchStatus.CANCELLED && match.getCancellingTeam() != null) {
            return match.getTeamOne().equals(match.getCancellingTeam())
                    ? match.getTeamTwo()
                    : match.getTeamOne();
        }
        return null;
    }

}
