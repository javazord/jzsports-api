package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.dto.TeamDTO;
import br.com.jzsports.tournament_control.model.entity.Team;
import br.com.jzsports.tournament_control.model.mapper.TeamMapper;
import br.com.jzsports.tournament_control.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    public TeamDTO save(Team team) {
        boolean existing = teamRepository.existsByNameAndChampionship_Id(team.getName(), team.getChampionship().getId());
        if (existing) {
            throw new RuntimeException("Already exists team with this name");
        }
        Team saved = teamRepository.save(team);
        return teamMapper.toDto(saved);
    }

    public TeamDTO findById(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Team not found"));
        return teamMapper.toDto(team);
    }

    public List<TeamDTO> findAllTeams() {
        List<Team> teamList = teamRepository.findAll();
        List<TeamDTO> teamsListDTO = teamList.stream().map(teamMapper::toDto).collect(Collectors.toList());
        return teamsListDTO;
    }

    public TeamDTO update(TeamDTO teamDTO) {
        Team existing = teamRepository.findById(teamDTO.getId()).orElseThrow(() -> new RuntimeException("Team not found"));
        teamMapper.updateTeam(teamDTO, existing);
        Team updated = teamRepository.save(existing);
        return teamMapper.toDto(updated);
    }


}
