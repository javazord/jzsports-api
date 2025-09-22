package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.entity.Championship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
    // 🔹 Campeonatos criados por um player
    List<Championship> findByCreatedBy_Id(Long playerId);

    // 🔹 Campeonatos nos quais o player está incluído via times
    // Agora seguimos Championship -> Team -> TeamMembership -> Player
    List<Championship> findDistinctByParticipants_Team_Memberships_Player_Id(Long playerId);

}
