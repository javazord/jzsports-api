package br.com.jzsports.tournament_control.controller;

import br.com.jzsports.tournament_control.model.dto.TeamDTO;
import br.com.jzsports.tournament_control.model.entity.Team;
import br.com.jzsports.tournament_control.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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
    public ResponseEntity<?> createTeam(@RequestBody Team team,  List<Long> idPlayersList) {
        TeamDTO teamDTO = teamService.save(team, idPlayersList);
        return ResponseEntity.status(HttpStatus.CREATED).body(teamDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateTeam(@RequestBody TeamDTO teamDTO,  List<Long> idPlayersList) {
        TeamDTO DTO = teamService.update(teamDTO, idPlayersList);
        return ResponseEntity.ok(DTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<TeamDTO>> getByNameAndPlayersList_Id(@RequestParam (value = "name", required = false) String name, @PathVariable Long id) {
        List<TeamDTO> dtoList = teamService.findAllByNameAndPlayersList_Id(name, id);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) {
        TeamDTO DTO = teamService.findById(id);
        return ResponseEntity.ok(DTO);
    }


}
