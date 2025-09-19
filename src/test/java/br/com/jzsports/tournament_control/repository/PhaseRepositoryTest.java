package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.e.EChampionshipStatus;
import br.com.jzsports.tournament_control.model.e.EMatchStatus;
import br.com.jzsports.tournament_control.model.e.ETypeChampionship;
import br.com.jzsports.tournament_control.model.e.ETypePhase;
import br.com.jzsports.tournament_control.model.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class PhaseRepositoryTest {

    @Autowired
    private PhaseRepository phaseRepository;

    @Autowired
    private ChampionshipRepository championshipRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MatchRepository matchRepository;

    private Player player;
    private Championship championship;
    private Team teamOne;
    private Team teamTwo;
    private Phase phase;
    private List<Match> matchList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        player = createPlayer();
        playerRepository.save(player);
        teamOne = createTeamOne();
        teamTwo = createTeamTwo();
        teamOne = teamRepository.save(teamOne);
        teamTwo = teamRepository.save(teamTwo);
        championship = createChampionship();
        championshipRepository.save(championship);
        phase = createPhase();
        phaseRepository.save(phase);
        matchList = createMatches();
        matchRepository.saveAll(matchList);

    }

    @Test
    @DisplayName("Should find phase by championship id")
    void findByChampionship_IdAndPhaseTypeCase01() {
        // executa método customizado
        List<Phase> result = phaseRepository.findByChampionship_IdAndPhaseType(championship.getId(), ETypePhase.FINAL);

        // valida
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPhaseType()).isEqualTo(ETypePhase.FINAL);
        assertThat(result.get(0).getChampionship().getId()).isEqualTo(championship.getId());
    }

    @Test
    @DisplayName("Should return empty list when phase not exists")
    void findByChampionship_IdAndPhaseTypeCase02() {
        List<Phase> result = phaseRepository.findByChampionship_IdAndPhaseType(championship.getId(), ETypePhase.QUARTER_FINAL);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should phases with matches")
    void savePhaseWithMatches(){
        phase.setMatchesList(matchList);
        assertThat(phase.getId()).isNotNull();
        assertThat(phase.getMatchesList()).hasSize(1);
        assertThat(phase.getMatchesList().get(0).getPhase()).isEqualTo(phase);
    }

    private Player createPlayer() {
        return new Player(
                null,
                "Mateus",
                "javazord",
                "123@",
                "mateus@gmail.com",
                "",
                "undefined",
                LocalDateTime.now(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    private Team createTeamOne() {
        return new Team(null, "TeamOne",null,null, null, null);
    }

    private Team createTeamTwo() {
        return new Team(null, "TeamTwo", null, null, null, null);
    }

    private Championship createChampionship() {
        return new Championship(null, "Fifa25", ETypeChampionship.FPS, EChampionshipStatus.IN_PROGRESS , LocalDate.now(), null, null, null, player);
    }

    private Phase createPhase() {
        return new Phase(null, ETypePhase.FINAL, null, matchList);
    }

    private List<Match> createMatches() {
        return Collections.singletonList(new Match(null, 0, 0, championship, teamOne, teamTwo, EMatchStatus.IN_PROGRESS, null,  null, null));
    }


}