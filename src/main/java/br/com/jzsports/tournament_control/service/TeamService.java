package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.dto.team.TeamDTO;
import br.com.jzsports.tournament_control.model.dto.team.TeamRequestDTO;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.entity.Team;
import br.com.jzsports.tournament_control.model.mapper.TeamMapper;
import br.com.jzsports.tournament_control.repository.PlayerRepository;
import br.com.jzsports.tournament_control.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper,  PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
        this.playerRepository = playerRepository;
    }

    public TeamDTO save(Team team) {
        if (team.getPlayersList() == null || team.getPlayersList().isEmpty()) {
            throw new IllegalArgumentException("A team must have at least one player");
        }

        List<Player> playersList = playerRepository.findAllById(team.getPlayersList().stream().map(Player::getId).collect(Collectors.toList()));
        if (playersList.size() != team.getPlayersList().size()) {
            throw new EntityNotFoundException("Some players were not found");
        }
        team.setPlayersList(playersList);
        Team saved = teamRepository.save(team);
        return teamMapper.toDto(saved);
    }

    public TeamDTO findById(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Team not found with id " + id));
        return teamMapper.toDto(team);
    }

    public List<TeamDTO> getAllByNameAndPlayersList_Id(String name, Long idPlayer) {
        List<Team> teamList = teamRepository.findByTeamNameAndPlayersList_Id(name, idPlayer);
        return teamList.stream().map(teamMapper::toDto).collect(Collectors.toList());
    }

    public List<TeamDTO> getAllByPlayersList_Id( Long idPlayer) {
        List<Team> teamList = teamRepository.findByPlayersList_Id(idPlayer);
        return teamList.stream().map(teamMapper::toDto).collect(Collectors.toList());
    }

    public TeamDTO update(TeamRequestDTO teamRequestDTO) {
        Team teamDB = teamRepository.findById(teamRequestDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));

        // Busca todos os jogadores pelos IDs
        List<Player> players = playerRepository.findAllById(teamRequestDTO.getIdPlayersList());
        if (players.size() != teamRequestDTO.getIdPlayersList().size()) {
            throw new EntityNotFoundException("Some players were not found");
        }
        teamDB.setPlayersList(players);
        teamMapper.updateTeam(teamRequestDTO, teamDB);
        Team updated = teamRepository.save(teamDB);
        return teamMapper.toDto(updated);
    }

    public List<TeamDTO> search(String teamName, LocalDate createdAt) {
        Team team = new Team();
        team.setTeamName(teamName);
        team.setCreatedAt(createdAt);
        Example<Team> example = Example.of(team, ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        List<Team> teamList = teamRepository.findAll(example);
        List<TeamDTO> teamDTOList = teamMapper.toDtoList(teamList);
        Collections.reverse(teamList);
        return teamDTOList;
    }


}
