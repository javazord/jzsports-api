package br.com.jzsports.tournament_control.controller;

import br.com.jzsports.tournament_control.model.dto.team.TeamDTO;
import br.com.jzsports.tournament_control.model.dto.team.TeamRequestDTO;
import br.com.jzsports.tournament_control.model.entity.Team;
import br.com.jzsports.tournament_control.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<?> createTeam(@RequestBody Team team) {
        TeamDTO teamDTO = teamService.save(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(teamDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateTeam(@RequestBody TeamRequestDTO teamRequestDTO) {
        TeamDTO DTO = teamService.update(teamRequestDTO);
        return ResponseEntity.ok(DTO);
    }

    @GetMapping("all/{id}")
    public ResponseEntity<List<TeamDTO>> getByNameAndPlayersList_Id(@RequestParam (value = "teamName", required = false) String teamName, @PathVariable Long id) {
        List<TeamDTO> dtoList = teamService.getAllByNameAndPlayersList_Id(teamName, id);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) {
        TeamDTO DTO = teamService.findById(id);
        return ResponseEntity.ok(DTO);
    }


}
