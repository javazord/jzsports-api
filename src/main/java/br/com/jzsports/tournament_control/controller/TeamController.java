package br.com.jzsports.tournament_control.controller;

import br.com.jzsports.tournament_control.model.dto.team.TeamDTO;
import br.com.jzsports.tournament_control.model.dto.team.TeamRequestDTO;
import br.com.jzsports.tournament_control.model.entity.Team;
import br.com.jzsports.tournament_control.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    // 🔹 Criar novo time
    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamRequestDTO dto) {
        TeamDTO teamDTO = teamService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(teamDTO);
    }

    // 🔹 Atualizar time existente
    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long id, @RequestBody TeamRequestDTO dto) {
        TeamDTO updated = teamService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // 🔹 Buscar todos os times filtrando por nome + jogador (via memberships)
    @GetMapping
    public ResponseEntity<List<TeamDTO>> search(
            @RequestParam(value = "teamName", required = false) String teamName,
            @RequestParam(value = "createdAt", required = false) LocalDate createdAt,
            @RequestParam(value = "playerId", required = false) Long playerId
    ) {
        List<TeamDTO> dtoList = teamService.search(teamName, createdAt, playerId);
        return ResponseEntity.ok(dtoList);
    }

    // 🔹 Buscar todos os times de um jogador específico
    @GetMapping("/all-player/{id}")
    public ResponseEntity<List<TeamDTO>> getTeamsByPlayer(@PathVariable Long id) {
        List<TeamDTO> dtoList = teamService.getAllByPlayerId(id);
        return ResponseEntity.ok(dtoList);
    }

    // 🔹 Buscar time pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) {
        TeamDTO dto = teamService.findById(id);
        return ResponseEntity.ok(dto);
    }
}

