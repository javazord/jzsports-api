package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.e.EMatchStatus;
import br.com.jzsports.tournament_control.model.entity.Match;
import br.com.jzsports.tournament_control.model.entity.Phase;
import br.com.jzsports.tournament_control.model.entity.Team;
import br.com.jzsports.tournament_control.repository.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatchServiceTest {

    private MatchRepository matchRepository;
    private MatchService matchService;

    @BeforeEach
    void setUp() {
        matchRepository = Mockito.mock(MatchRepository.class);
        matchService = new MatchService(matchRepository);
    }

    // -------------------------------
    // TESTES DO createMatchesForPhase
    // -------------------------------

    @Test
    void createMatchesForPhase_case1_sucesso() {
        Phase phase = new Phase();
        Team team1 = new Team();
        team1.setTeamName("Team A");
        Team team2 = new Team();
        team2.setTeamName("Team B");

        List<Team> teams = Arrays.asList(team1, team2);

        when(matchRepository.saveAll(anyList())).thenAnswer(i -> i.getArguments()[0]);

        List<Match> matches = matchService.createMatchesForPhase(phase, teams);

        assertEquals(1, matches.size());
        assertEquals(team1, matches.get(0).getTeamOne());
        assertEquals(team2, matches.get(0).getTeamTwo());
        assertEquals(EMatchStatus.IN_PROGRESS, matches.get(0).getStatus());
    }

    @Test
    void createMatchesForPhase_case2_falhaEsperada() {
        Phase phase = new Phase();
        Team team1 = new Team();
        Team team2 = new Team();

        List<Team> teams = Arrays.asList(team1, team2);

        when(matchRepository.saveAll(anyList())).thenAnswer(i -> i.getArguments()[0]);

        List<Match> matches = matchService.createMatchesForPhase(phase, teams);

        // proposital: esperamos 2, mas na verdade é 1 -> teste falha
        assertEquals(2, matches.size());
    }

    // -------------------------------
    // TESTES DO getWinner
    // -------------------------------

    @Test
    void getWinner_case1_sucesso() {
        Team team1 = new Team();
        team1.setTeamName("Team A");
        Team team2 = new Team();
        team2.setTeamName("Team B");

        Match match = new Match();
        match.setTeamOne(team1);
        match.setTeamTwo(team2);
        match.setScoreTeamOne(3);
        match.setScoreTeamTwo(1);
        match.setStatus(EMatchStatus.FINISHED);

        Team winner = matchService.getWinner(match);

        assertEquals(team1, winner);
    }

    @Test
    void getWinner_case2_falhaEsperada() {
        Team team1 = new Team();
        team1.setTeamName("Team A");
        Team team2 = new Team();
        team2.setTeamName("Team B");

        Match match = new Match();
        match.setTeamOne(team1);
        match.setTeamTwo(team2);
        match.setScoreTeamOne(3);
        match.setScoreTeamTwo(1);
        match.setStatus(EMatchStatus.FINISHED);

        Team winner = matchService.getWinner(match);

        // proposital: esperamos team2, mas o certo é team1 -> teste falha
        assertEquals(team2, winner);
    }

    // -------------------------------
    // TESTES DO getWinnerFromCancellation
    // -------------------------------

    @Test
    void getWinnerFromCancellation_case1_sucesso() {
        Team team1 = new Team();
        team1.setTeamName("Team A");
        Team team2 = new Team();
        team2.setTeamName("Team B");

        Match match = new Match();
        match.setTeamOne(team1);
        match.setTeamTwo(team2);
        match.setStatus(EMatchStatus.CANCELLED);
        match.setCancellingTeam(team1); // team1 desistiu

        Team winner = matchService.getWinnerFromCancellation(match);

        assertEquals(team2, winner);
    }

    @Test
    void getWinnerFromCancellation_case2_falhaEsperada() {
        Team team1 = new Team();
        Team team2 = new Team();

        Match match = new Match();
        match.setTeamOne(team1);
        match.setTeamTwo(team2);
        match.setStatus(EMatchStatus.CANCELLED);
        match.setCancellingTeam(team1);

        Team winner = matchService.getWinnerFromCancellation(match);

        // proposital: esperamos o time errado -> falha
        assertEquals(team1, winner);
    }
}
