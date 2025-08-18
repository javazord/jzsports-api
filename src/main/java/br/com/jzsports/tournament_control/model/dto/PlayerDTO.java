package br.com.jzsports.tournament_control.model.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class PlayerDTO {

    private Long id;
    private String name;
    private String nickname;
    private String email;
    private String photoURL;

    // Lista de campeonatos criados (opcional para evitar loop no JSON)
    @JsonIgnore
    private List<ChampionshipDTO> championshipsCreated;
}
