package br.com.jzsports.tournament_control.model.dto.championship;
import br.com.jzsports.tournament_control.model.dto.phase.PhaseDTO;
import br.com.jzsports.tournament_control.model.dto.team.TeamDTO;
import br.com.jzsports.tournament_control.model.dto.player.PlayerSimpleDTO;
import br.com.jzsports.tournament_control.model.e.EChampionshipStatus;
import br.com.jzsports.tournament_control.model.e.ETypeChampionship;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ChampionshipDTO {

    private Long id;
    private String championshipName;
    private String championshipTypeDescription;
    private String championshipStatusDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    private PlayerSimpleDTO createdBy;
    private List<TeamDTO> teamList;
}
