package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
