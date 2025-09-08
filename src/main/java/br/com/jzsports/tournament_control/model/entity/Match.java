package br.com.jzsports.tournament_control.model.entity;
import br.com.jzsports.tournament_control.model.e.EMatchStatus;
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

    @Column
    private Integer scoreTeamOne;

    @Column
    private Integer scoreTeamTwo;

    @ManyToOne
    @JoinColumn(name = "championship_id")
    private Championship championship;

    @ManyToOne
    @JoinColumn(name = "team_one_id")
    private Team teamOne;

    @ManyToOne
    @JoinColumn(name = "team_two_id")
    private Team teamTwo;

    @Column
    @Enumerated(EnumType.STRING)
    private EMatchStatus status;

    @ManyToOne
    @JoinColumn(name = "phase_id", nullable = false)
    private Phase phase;

    @ManyToOne
    @JoinColumn(name = "cancelling_team_id")
    private Team cancellingTeam;

}
