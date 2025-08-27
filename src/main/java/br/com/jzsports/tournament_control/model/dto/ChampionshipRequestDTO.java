package br.com.jzsports.tournament_control.model.dto;

import br.com.jzsports.tournament_control.model.entity.Championship;
import br.com.jzsports.tournament_control.model.entity.Player;
import lombok.Data;

@Data
public class ChampionshipRequestDTO {

    private ChampionshipDTO championship;
    private Player loggedPlayer;

}
