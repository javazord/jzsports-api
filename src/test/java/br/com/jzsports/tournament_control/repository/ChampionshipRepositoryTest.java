package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.entity.Championship;
import br.com.jzsports.tournament_control.model.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ChampionshipRepositoryTest {

    @Autowired
    private ChampionshipRepository championshipRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private Player player;

    @BeforeEach
    void setUp() {
        player = createPlayer();
        Championship championship = createChampionship();
        playerRepository.save(player);
        championshipRepository.save(championship);
    }

    @Test
    @DisplayName("Should find created player by id")
    void findByCreatedBy_Id() {
        List<ChampionshipDTO> result = championshipRepository.findByCreatedBy_Id(player.getId());

        assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .allSatisfy(ch -> {
                    assertThat(ch.getCreatedBy().getId()).isEqualTo(player.getId());
                    assertThat(ch.getName()).isEqualTo("Championship Test");
                });
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
                null,
                null
        );
    }

    public Championship createChampionship() {
        return new Championship(null, "Championship Test", "Fifa25", LocalDate.now(), null, null, null, player);
    }

}