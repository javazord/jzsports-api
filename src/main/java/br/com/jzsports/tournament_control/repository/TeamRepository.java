package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findDistinctByMemberships_Player_Id(Long id);
    // 🔹 Pesquisa flexível com filtros opcionais
    @Query("""
        SELECT DISTINCT t
        FROM Team t
        LEFT JOIN t.memberships m
        WHERE (:teamName IS NULL OR LOWER(t.teamName) LIKE LOWER(CONCAT('%', :teamName, '%')))
          AND (:createdAt IS NULL OR t.createdAt = :createdAt)
          AND (:playerId IS NULL OR m.player.id = :playerId)
    """)
    List<Team> search(
            @Param("teamName") String teamName,
            @Param("createdAt") LocalDate createdAt,
            @Param("playerId") Long playerId
    );


}
