package br.com.jzsports.tournament_control.model.dto.match;

import br.com.jzsports.tournament_control.model.dto.player.PlayerDTO;
import br.com.jzsports.tournament_control.model.dto.team.TeamDTO;
import br.com.jzsports.tournament_control.model.entity.Match;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchParticipantDTO {

    private Long id;
    private Long matchId;
    private TeamDTO team;
    private PlayerDTO player;
    private Integer score;
    private Boolean winner;
    private boolean cancelled;

}
