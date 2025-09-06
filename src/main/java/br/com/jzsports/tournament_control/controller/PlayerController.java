package br.com.jzsports.tournament_control.controller;

import br.com.jzsports.tournament_control.model.dto.player.PlayerDTO;
import br.com.jzsports.tournament_control.model.dto.player.PlayerProfDTO;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping
    public ResponseEntity<?> savePlayer(@RequestBody Player player) {
        PlayerDTO playerDTO = playerService.save(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(playerDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO playerDTO) {
        PlayerDTO DTO = playerService.update(id, playerDTO);
        return ResponseEntity.ok(DTO);
    }

    @PutMapping("/{id}/player-profile")
    public ResponseEntity<?> updatePlayerProfile(@PathVariable Long id, @RequestBody PlayerProfDTO playerProfDTO) {
        PlayerDTO DTO = playerService.updateProfile(id, playerProfDTO);
        return ResponseEntity.ok(DTO);
    }

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        List<PlayerDTO> playerDTOS = playerService.findAll();
        return ResponseEntity.ok(playerDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlayerById(@PathVariable Long id) {
        PlayerDTO playerDTO = playerService.findById(id);
        return ResponseEntity.ok(playerDTO);
    }

}
