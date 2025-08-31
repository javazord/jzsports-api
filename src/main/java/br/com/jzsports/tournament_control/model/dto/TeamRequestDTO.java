package br.com.jzsports.tournament_control.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
