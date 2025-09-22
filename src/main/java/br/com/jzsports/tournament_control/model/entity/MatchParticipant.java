package br.com.jzsports.tournament_control.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MatchParticipant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column
    private Integer score;

    @Column
    private Boolean winner;

    @Column(nullable = false)
    private boolean cancelled;
}
