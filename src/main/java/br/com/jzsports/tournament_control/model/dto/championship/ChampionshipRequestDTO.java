package br.com.jzsports.tournament_control.model.dto.championship;

import br.com.jzsports.tournament_control.model.entity.Player;
import lombok.Data;

@Data
public class ChampionshipRequestDTO {

    private ChampionshipDTO championship;
    private Player loggedPlayer;

}
