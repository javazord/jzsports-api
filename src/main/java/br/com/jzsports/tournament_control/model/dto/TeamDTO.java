package br.com.jzsports.tournament_control.model.dto;
import br.com.jzsports.tournament_control.model.entity.Player;
import lombok.Data;

@Data
public class TeamDTO {

    private Long id;
    private String name;
    private PlayerDTO player;
    private ChampionshipDTO championship;

}
