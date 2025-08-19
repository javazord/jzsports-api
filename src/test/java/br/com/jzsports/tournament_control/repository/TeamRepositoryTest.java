package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.entity.Championship;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ChampionshipRepository championshipRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {
        // cria jogador
        Player player = createPlayer();
        playerRepository.save(player);

        // cria time
        Team team = new Team();
        team.setName("Team A");
        teamRepository.save(team);

        // cria campeonato
        Championship championship = new Championship();
        championship.setName("Championship 1");
        championship.setCreatedBy(player);
        championship.setTeamsList(new ArrayList<>());
        championship.getTeamsList().add(team);
        championshipRepository.save(championship);

    }

    @Test
    @DisplayName("Deve retornar true se existir time com o nome em um campeonato")
    void existsByNameAndChampionshipList_IdCase1() {
    }

    @Test
    @DisplayName("Deve retornar false se não existir time com o nome em um campeonato")
    void existsByNameAndChampionshipList_IdCase2() {
    }

    private Player createPlayer() {
        return new Player(
                null,
                "Mateus",
                "javazord",
                "123@",
                "mateus@gmail.com",
                "undefined",
                LocalDateTime.now(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}