package br.com.jzsports.tournament_control.model.dto.team;

import br.com.jzsports.tournament_control.model.dto.player.PlayerDTO;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamMembershipDTO {

    private Long id;
    private TeamDTO team;
    private PlayerDTO player;
    private LocalDate joinedAt;
    private LocalDate leftAt;
}
