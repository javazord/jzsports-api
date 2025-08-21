package br.com.jzsports.tournament_control.model.dto;
import br.com.jzsports.tournament_control.model.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {

    private Long id;
    private String name;
    private String photoURL;
    private List<PlayerDTO> player;
    private List<ChampionshipDTO> championship;

}
