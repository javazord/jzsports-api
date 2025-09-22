package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.entity.TeamMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMembershipRepository extends JpaRepository<TeamMembership, Long> {
    // Buscar todos os vínculos de um time
    List<TeamMembership> findByTeam_Id(Long teamId);

    // Buscar todos os vínculos de um jogador
    List<TeamMembership> findByPlayer_Id(Long playerId);

    // Remover vínculos de um time (usado no update)
    void deleteByTeam_Id(Long teamId);

    // Verificar se jogador já está em um time
    boolean existsByPlayer_IdAndTeam_Id(Long playerId, Long teamId);
}
