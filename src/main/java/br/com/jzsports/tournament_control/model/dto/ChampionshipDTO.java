package br.com.jzsports.tournament_control.model.dto;
import br.com.jzsports.tournament_control.model.entity.Match;
import br.com.jzsports.tournament_control.model.entity.Team;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ChampionshipDTO {

    private Long id;
    private String name;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<TeamDTO> teams;
    private List<MatchDTO> matches;

}
