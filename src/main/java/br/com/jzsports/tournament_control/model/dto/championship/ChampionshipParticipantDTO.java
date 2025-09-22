package br.com.jzsports.tournament_control.model.dto.championship;

import br.com.jzsports.tournament_control.model.entity.Championship;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChampionshipParticipantDTO {

    private Long id;
    private Long championshipId;
    private Team team;
    private Player player;
}
