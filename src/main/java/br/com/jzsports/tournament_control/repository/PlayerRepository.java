package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsPlayerByEmailOrNickname(String email, String nickName);
}
