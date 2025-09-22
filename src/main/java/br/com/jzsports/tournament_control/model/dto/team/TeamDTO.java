package br.com.jzsports.tournament_control.model.dto.team;
import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.dto.player.PlayerDTO;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.entity.TeamMembership;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    // Jogadores do time (extraído de memberships)
    private List<PlayerDTO> players;
}
