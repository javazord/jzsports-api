package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByTeamNameAndPlayersList_Id(String name, Long id);
    List<Team> findByPlayersList_Id(Long id);
    @Query("SELECT t FROM Team t JOIN t.playersList p " +
            "WHERE (:teamName IS NULL OR LOWER(t.teamName) LIKE LOWER(CONCAT('%', :teamName, '%'))) " +
            "AND (:createdAt IS NULL OR t.createdAt = :createdAt) " +
            "AND (:playerId IS NULL OR p.id = :playerId)")
    List<Team> searchTeams(@Param("teamName") String teamName,
                           @Param("createdAt") LocalDate createdAt,
                           @Param("playerId") Long playerId);

}
