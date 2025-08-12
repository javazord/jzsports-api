package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.entity.Championship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
}
