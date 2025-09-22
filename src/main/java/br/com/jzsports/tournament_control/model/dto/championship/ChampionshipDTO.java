package br.com.jzsports.tournament_control.model.dto.championship;
import br.com.jzsports.tournament_control.model.dto.phase.PhaseDTO;
import br.com.jzsports.tournament_control.model.dto.team.TeamDTO;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ChampionshipDTO {
    private Long id;
    private String championshipName;
    private String championshipType;
    private String championshipStatus;
    private LocalDate createdAt;
    private LocalDate startDate;
    private LocalDate endDate;

    // Player criador
    private Long createdByPlayerId;

    // Times participantes
    private List<Long> teamIds;

}
