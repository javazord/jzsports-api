package br.com.jzsports.tournament_control.model.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "game_match")
public class Match implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "championship_id")
    private Championship championship;
    @ManyToOne
    @JoinColumn(name = "player_one_id")
    private Player playerOne;
    @ManyToOne
    @JoinColumn(name = "player_two_id")
    private Player playerTwo;
    @Column
    private Integer scorePlayer1;
    @Column
    private Integer scorePlayer2;

}
