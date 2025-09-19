package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.entity.Championship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
    List<ChampionshipDTO> findByCreatedBy_Id(Long playerId);

    @Query("""
    SELECT DISTINCT c FROM Championship c
    JOIN FETCH c.teamsList t
    JOIN FETCH t.playersList p
    WHERE p.id = :playerId
""")
    List<Championship> findByPlayerIncluded(Long playerId);
}
