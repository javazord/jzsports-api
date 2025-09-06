package br.com.jzsports.tournament_control.model.dto.team;

import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamRequestDTO {

    private Long id;
    private String teamName;
    private String photoURL;
    private List<Long> idPlayersList;
    private List<ChampionshipDTO> championship;

}
