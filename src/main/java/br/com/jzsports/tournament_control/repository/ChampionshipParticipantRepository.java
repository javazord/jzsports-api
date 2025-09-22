package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.entity.ChampionshipParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionshipParticipantRepository extends JpaRepository<ChampionshipParticipant, Long> {
}
