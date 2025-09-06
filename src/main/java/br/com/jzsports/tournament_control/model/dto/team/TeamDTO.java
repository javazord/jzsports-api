package br.com.jzsports.tournament_control.model.dto.team;
import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.dto.player.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {

    private Long id;
    private String teamName;
    private String photoURL;
    private LocalDate createdAt;
    private List<PlayerDTO> players;
    private List<ChampionshipDTO> championship;

}
