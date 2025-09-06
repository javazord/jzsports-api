package br.com.jzsports.tournament_control.model.dto.championship;
import br.com.jzsports.tournament_control.model.dto.team.TeamDTO;
import br.com.jzsports.tournament_control.model.dto.player.PlayerSimpleDTO;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ChampionshipDTO {

    private Long id;
    private String name;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;

    // Lista de times do campeonato
    private List<TeamDTO> teamsList;

    // Jogador que criou o campeonato (somente ID e nome para evitar loop)
    private PlayerSimpleDTO createdBy;
}
