package br.com.jzsports.tournament_control.model.dto;
import br.com.jzsports.tournament_control.model.entity.Championship;
import br.com.jzsports.tournament_control.model.entity.Player;
import lombok.Data;

@Data
public class MatchDTO {

    private Long id;
    private ChampionshipDTO championship;
    private PlayerDTO playerOne;
    private PlayerDTO playerTwo;
    private Integer scorePlayer1;
    private Integer scorePlayer2;

}
