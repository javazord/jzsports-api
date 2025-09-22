package br.com.jzsports.tournament_control.model.dto.championship;

import br.com.jzsports.tournament_control.model.entity.Player;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ChampionshipRequestDTO {
    private String championshipName;
    private String championshipType; // ENUM string
    private LocalDate startDate;
    private LocalDate endDate;
    private Long createdByPlayerId;
    private List<Long> teamIds; // IDs dos times que participam
}
