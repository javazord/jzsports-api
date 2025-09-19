package br.com.jzsports.tournament_control.controller;

import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipRequestDTO;
import br.com.jzsports.tournament_control.model.entity.Championship;
import br.com.jzsports.tournament_control.model.mapper.ChampionshipMapper;
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

    private final ChampionshipService championshipService;
    private final ChampionshipMapper championshipMapper;

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

//    @GetMapping("/{id}/createdBy")
//    public ResponseEntity<?> getChampionshipByCreated(@RequestBody Championship champ){
//        List<ChampionshipDTO> dto = championshipService.getChampionshipsByCreatedBy(champ.getCreatedBy().getId());
//        return ResponseEntity.ok(dto);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChampionshipPlayerIncluded(@PathVariable Long id){
        List<Championship> championship = championshipService.getByPlayerIncluded(id);
        List<ChampionshipDTO> dtoList = championshipMapper.toDtoList(championship);
        return ResponseEntity.ok(dtoList);
    }

}
