package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.entity.Championship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
    List<ChampionshipDTO> findByCreatedBy_Id(Long playerId);
}
