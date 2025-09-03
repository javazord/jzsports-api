package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class PlayerRepositoryTest {

    @Autowired
    PlayerRepository playerRepository;

    private Player player;

    @BeforeEach
    void setUp() {
        player = createPlayer();
        this.playerRepository.save(player);
    }

    @Test
    @DisplayName("Should exists Player by email or nickname from database")
    void existsPlayerByEmailOrNicknameCase1() {
        boolean existsPlayerByEmailOrNickname = this.playerRepository.existsPlayerByEmailOrNickname( player.getEmail(), player.getNickname());

        assertThat(existsPlayerByEmailOrNickname).isTrue();

    }

    @Test
    @DisplayName("Should not exists Player by email or nickname from database")
    void existsPlayerByEmailOrNicknameCase2() {
        String email = "teste@gmail.com";
        String nickname = "teste";
        boolean existsPlayerByEmailOrNickname = this.playerRepository.existsPlayerByEmailOrNickname( email, nickname);

        assertThat(existsPlayerByEmailOrNickname).isFalse();

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



}