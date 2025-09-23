package br.com.jzsports.tournament_control.controller;

import br.com.jzsports.tournament_control.model.dto.phase.PhaseDTO;
import br.com.jzsports.tournament_control.model.dto.phase.PhaseResponseDTO;
import br.com.jzsports.tournament_control.model.entity.Phase;
import br.com.jzsports.tournament_control.model.mapper.PhaseMapper;
import br.com.jzsports.tournament_control.service.PhaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/phases")
public class PhaseController {

    private final PhaseService phaseService;
    private final PhaseMapper phaseMapper;

    @PostMapping("/{id}")
    public ResponseEntity<?> createPhase(@RequestBody Phase phase, @PathVariable Long id) {
        phaseService.generateNextPhase(id, phase.getPhaseType());
        PhaseDTO dto = phaseMapper.toDto(phase);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhaseResponseDTO> getByChampionshipId(@PathVariable Long id) {
        PhaseResponseDTO dto = phaseService.findByChampionshipId(id);
        return ResponseEntity.ok().body(dto);
    }

}
