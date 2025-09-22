package br.com.jzsports.tournament_control.model.dto.phase;
import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.dto.match.MatchDTO;
import br.com.jzsports.tournament_control.model.e.ETypePhase;
import br.com.jzsports.tournament_control.model.entity.Championship;
import br.com.jzsports.tournament_control.model.entity.Match;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class PhaseDTO {

    private Long id;
    private String phaseTypeDescription;
    private Long championshipId;
    private ETypePhase phaseType;
    private List<Match> matches;

}
