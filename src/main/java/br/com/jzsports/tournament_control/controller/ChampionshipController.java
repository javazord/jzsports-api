package br.com.jzsports.tournament_control.controller;

import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipRequestDTO;
import br.com.jzsports.tournament_control.model.entity.Championship;
import br.com.jzsports.tournament_control.service.ChampionshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/championships")
public class ChampionshipController {

    private ChampionshipService championshipService;

    @PostMapping
    public ResponseEntity<?> createChampionship(@RequestBody Championship champ){
        ChampionshipDTO dto = championshipService.save(champ);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateChampionship(@PathVariable Long id, @RequestBody ChampionshipRequestDTO  champ) {
        ChampionshipDTO championshipDTO = championshipService.updateChampionship(id, champ.getLoggedPlayer(), champ.getChampionship());
        return ResponseEntity.ok(championshipDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChampionshipByPlayer(@RequestBody Championship champ){
        List<ChampionshipDTO> dto = championshipService.getChampionshipsByPlayer(champ.getCreatedBy().getId());
        return ResponseEntity.ok(dto);
    }

}
