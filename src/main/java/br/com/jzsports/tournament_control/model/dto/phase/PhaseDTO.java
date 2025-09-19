package br.com.jzsports.tournament_control.model.dto.phase;
import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.dto.match.MatchDTO;
import br.com.jzsports.tournament_control.model.e.ETypePhase;
import lombok.Data;

import java.util.List;

@Data
public class PhaseDTO {

    private Long id;
    private String phaseTypeDescription;
    private ChampionshipDTO championship;
    private List<MatchDTO> matchesList;

}
