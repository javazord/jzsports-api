package br.com.jzsports.tournament_control.model.dto.player;
import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {

    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String color;
    private String photoURL;

}
