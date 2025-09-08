package br.com.jzsports.tournament_control.model.dto.player;
import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class PlayerDTO {

    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String color;
    private String photoURL;
    // Lista de campeonatos criados (opcional para evitar loop no JSON)
    @JsonIgnore
    private List<ChampionshipDTO> championshipList;
}
