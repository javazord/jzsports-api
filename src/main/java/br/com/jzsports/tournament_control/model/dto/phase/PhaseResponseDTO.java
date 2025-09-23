package br.com.jzsports.tournament_control.model.dto.phase;

import br.com.jzsports.tournament_control.model.dto.match.MatchDTO;
import lombok.Data;

import java.util.List;

@Data
public class PhaseResponseDTO {

    private Long id;
    private String phaseType;
    private Long championshipId;
    private String championshipName;
    private List<MatchDTO> matches;

}
