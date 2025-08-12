package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
