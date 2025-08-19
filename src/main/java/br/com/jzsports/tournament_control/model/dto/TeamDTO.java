package br.com.jzsports.tournament_control.model.dto;
import br.com.jzsports.tournament_control.model.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {

    private Long id;
    private String name;
    private PlayerDTO player;
    private ChampionshipDTO championship;

}
