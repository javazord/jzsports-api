package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    boolean existsByNameAndChampionship_Id(String name, Long id);

}
