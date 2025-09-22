package br.com.jzsports.tournament_control.repository;

import br.com.jzsports.tournament_control.model.entity.MatchParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchParticipantRepository extends JpaRepository<MatchParticipant, Long> {
    // Buscar todos os participantes de uma partida
    List<MatchParticipant> findByMatch_Id(Long matchId);

    // Buscar todas as partidas em que um time participou
    List<MatchParticipant> findByTeam_Id(Long teamId);

    // Verificar se um time já está vinculado a uma partida
    boolean existsByMatch_IdAndTeam_Id(Long matchId, Long teamId);

    // Remover todos os participantes de uma partida
    void deleteByMatch_Id(Long matchId);
}
