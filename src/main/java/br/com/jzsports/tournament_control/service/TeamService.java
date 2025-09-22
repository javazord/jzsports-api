package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.dto.team.TeamDTO;
import br.com.jzsports.tournament_control.model.dto.team.TeamRequestDTO;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.entity.Team;
import br.com.jzsports.tournament_control.model.entity.TeamMembership;
import br.com.jzsports.tournament_control.model.mapper.TeamMapper;
import br.com.jzsports.tournament_control.repository.PlayerRepository;
import br.com.jzsports.tournament_control.repository.TeamMembershipRepository;
import br.com.jzsports.tournament_control.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final TeamMapper teamMapper;

    // 🔹 Criar time
    @Transactional
    public TeamDTO save(TeamRequestDTO dto) {
        Team team = new Team();
        team.setTeamNameAndPhoto(dto);

        // Adiciona os players enviados
        for (Long playerId : dto.getPlayersIds()) {
            Player player = playerRepository.findById(playerId)
                    .orElseThrow(() -> new EntityNotFoundException("Player not found with id " + playerId));
            team.addMembership(player);
        }

        Team saved = teamRepository.save(team);
        return teamMapper.toDto(saved);
    }

    // 🔹 Atualizar time
    @Transactional
    public TeamDTO update(Long id, TeamRequestDTO dto) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id " + id));

        team.setTeamName(dto.getTeamName());
        team.setPhotoURL(dto.getPhotoURL());

        // Se atualizar jogadores, recria memberships
        if (dto.getPlayersIds() != null) {
            team.getMemberships().clear();

            for (Long playerId : dto.getPlayersIds()) {
                Player player = playerRepository.findById(playerId)
                        .orElseThrow(() -> new EntityNotFoundException("Player not found with id " + playerId));

                TeamMembership membership = new TeamMembership();
                membership.setPlayer(player);
                membership.setTeam(team);

                team.getMemberships().add(membership);
            }
        }

        Team updated = teamRepository.save(team);
        return teamMapper.toDto(updated);
    }

    // 🔹 Buscar time pelo ID
    public TeamDTO findById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id " + id));
        return teamMapper.toDto(team);
    }

    // 🔹 Buscar todos os times de um jogador
    public List<TeamDTO> getAllByPlayerId(Long playerId) {
        List<Team> teams = teamRepository.findDistinctByMemberships_Player_Id(playerId);
        return teamMapper.toDtoList(teams);
    }

    // 🔹 Buscar com filtros opcionais
    public List<TeamDTO> search(String teamName, LocalDate createdAt, Long playerId) {
        List<Team> teams = teamRepository.search(teamName, createdAt, playerId);
        return teamMapper.toDtoList(teams);
    }
}
