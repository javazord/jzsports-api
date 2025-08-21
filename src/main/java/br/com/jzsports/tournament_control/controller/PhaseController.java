package br.com.jzsports.tournament_control.controller;

import br.com.jzsports.tournament_control.model.dto.PhaseDTO;
import br.com.jzsports.tournament_control.model.entity.Phase;
import br.com.jzsports.tournament_control.service.PhaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/phases")
public class PhaseController {

    private final PhaseService phaseService;

    @PostMapping("/{id}")
    public ResponseEntity<?> createPhase(@RequestBody Phase phase, @PathVariable Long idChampionship) {
        phaseService.generateNextPhase(idChampionship, phase.getPhase());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhaseDTO> getPhase(@PathVariable Long id) {
        PhaseDTO dto = phaseService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

}
