package br.com.jzsports.tournament_control.model.dto.match;
import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.dto.player.PlayerDTO;
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
