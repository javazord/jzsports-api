package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.entity.Phase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.annotation.ElementType;
import java.util.List;

public interface PhaseRepository extends JpaRepository<Phase, Long> {
    List<Phase> findByCampionship_IdAndPhase(Long campionshipId, ElementType currentPhase);
}
