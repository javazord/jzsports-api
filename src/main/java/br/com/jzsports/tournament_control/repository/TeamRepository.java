package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByTeamNameAndPlayersList_Id(String name, Long id);
}
