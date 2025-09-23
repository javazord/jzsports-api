package br.com.jzsports.tournament_control.model.dto.match;
import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.dto.player.PlayerDTO;
import br.com.jzsports.tournament_control.model.dto.team.TeamDTO;
import br.com.jzsports.tournament_control.model.e.EMatchStatus;
import br.com.jzsports.tournament_control.model.entity.Championship;
import br.com.jzsports.tournament_control.model.entity.MatchParticipant;
import br.com.jzsports.tournament_control.model.entity.Phase;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class MatchDTO {

    private Long id;
    private String status;
    private ChampionshipDTO championship;
    private Long phaseId;
    private List<MatchParticipantDTO> participants;

}
