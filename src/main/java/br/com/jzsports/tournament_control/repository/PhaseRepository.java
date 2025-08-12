package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.entity.Phase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhaseRepository extends JpaRepository<Phase, Long> {
}
