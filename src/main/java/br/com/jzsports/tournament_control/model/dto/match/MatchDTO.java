package br.com.jzsports.tournament_control.model.dto.match;
import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.dto.player.PlayerDTO;
import br.com.jzsports.tournament_control.model.dto.team.TeamDTO;
import br.com.jzsports.tournament_control.model.e.EMatchStatus;
import lombok.Data;

import java.util.List;

@Data
public class MatchDTO {

    private Long id;
    private ChampionshipDTO championship;
    private TeamDTO teamOne;
    private TeamDTO teamTwo;
    private Integer scoreTeamOne;
    private Integer scoreTeamTwo;
    private EMatchStatus status;
    private TeamDTO cancellingTeam;

}
