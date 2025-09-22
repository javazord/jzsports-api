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
    private String teamName;
    private String photoURL;
    private List<Long> playersIds; // IDs dos jogadores que compõem o time
}
