package br.com.jzsports.tournament_control.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import br.com.jzsports.tournament_control.model.e.ETypePhase;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Phase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ETypePhase phase;
    @ManyToOne
    @JoinColumn(name = "team_one_id")
    private Team teamOne;
    @ManyToOne
    @JoinColumn(name = "team_two_id")
    private Team teamTwo;
    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Team winner;

}
