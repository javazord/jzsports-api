package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.e.EMatchStatus;
import br.com.jzsports.tournament_control.model.entity.*;
import br.com.jzsports.tournament_control.repository.MatchRepository;
import br.com.jzsports.tournament_control.repository.MatchParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final MatchParticipantRepository matchParticipantRepository;

    // 🔹 Cria partidas para uma fase (inclui bye automático)
    public List<Match> createMatchesForPhase(Phase phase, List<Team> teams) {
        List<Match> matches = new ArrayList<>();

        // Se número ímpar de times, cria bye automático
        if (teams.size() % 2 != 0) {
            Team byeTeam = teams.remove(teams.size() - 1);

            Match byeMatch = new Match();
            byeMatch.setPhase(phase);
            byeMatch.setChampionship(phase.getChampionship());
            byeMatch.setStatus(EMatchStatus.FINISHED);

            Match savedByeMatch = matchRepository.save(byeMatch);

            // Participante
            MatchParticipant participant = new MatchParticipant();
            participant.setMatch(savedByeMatch);
            participant.setTeam(byeTeam);
            participant.setScore(0);
            participant.setWinner(true);
            matchParticipantRepository.save(participant);

            matches.add(savedByeMatch);
        }

        // Cria os matches normais (pares)
        for (int i = 0; i < teams.size(); i += 2) {
            Match match = new Match();
            match.setPhase(phase);
            match.setChampionship(phase.getChampionship());
            match.setStatus(EMatchStatus.IN_PROGRESS);
            Match savedMatch = matchRepository.save(match);

            MatchParticipant p1 = new MatchParticipant();
            p1.setMatch(savedMatch);
            p1.setTeam(teams.get(i));
            p1.setScore(0);

            MatchParticipant p2 = new MatchParticipant();
            p2.setMatch(savedMatch);
            p2.setTeam(teams.get(i + 1));
            p2.setScore(0);

            matchParticipantRepository.saveAll(List.of(p1, p2));

            matches.add(savedMatch);
        }
        return matches;
    }

    // 🔹 Determina o vencedor de uma partida
    public Team getWinner(Match match) {
        if (match.getStatus() != EMatchStatus.FINISHED) {
            return null;
        }

        return match.getParticipants().stream()
                .filter(MatchParticipant::getWinner)
                .map(MatchParticipant::getTeam)
                .findFirst()
                .orElse(null);
    }

    // 🔹 Vencedor no caso de cancelamento
    public Team getWinnerFromCancellation(Match match, Team cancellingTeam) {
        return match.getParticipants().stream()
                .map(MatchParticipant::getTeam)
                .filter(team -> !team.equals(cancellingTeam))
                .findFirst()
                .orElse(null);
    }
}
