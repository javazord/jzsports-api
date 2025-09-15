package br.com.jzsports.tournament_control.model.dto.phase;
import br.com.jzsports.tournament_control.model.dto.match.MatchDTO;
import br.com.jzsports.tournament_control.model.dto.team.TeamDTO;
import br.com.jzsports.tournament_control.model.e.ETypePhase;
import lombok.Data;

import java.util.List;

@Data
public class PhaseDTO {

    private Long id;
    private ETypePhase phase;
    private TeamDTO teamOne;
    private TeamDTO teamTwo;
    private TeamDTO winner;
    private List<MatchDTO> matches;

}
