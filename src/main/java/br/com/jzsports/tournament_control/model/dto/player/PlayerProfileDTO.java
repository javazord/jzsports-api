package br.com.jzsports.tournament_control.model.dto.player;

import lombok.Data;

@Data
public class PlayerProfileDTO {

    private Long id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String color;
    private String photoURL;

}
