package br.com.jzsports.tournament_control.model.dto;
import lombok.Data;

import java.util.List;

@Data
public class PlayerDTO {

    private Long id;
    private String name;
    private String nickname;
    private String email;
    private List<TeamDTO> teams;

}
