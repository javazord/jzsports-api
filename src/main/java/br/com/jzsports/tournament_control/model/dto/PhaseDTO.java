package br.com.jzsports.tournament_control.model.dto;
import br.com.jzsports.tournament_control.model.e.ETypePhase;
import lombok.Data;

@Data
public class PhaseDTO {

    private Long id;
    private ETypePhase phase;
    private TeamDTO teamOne;
    private TeamDTO teamTwo;
    private TeamDTO winner;

}
