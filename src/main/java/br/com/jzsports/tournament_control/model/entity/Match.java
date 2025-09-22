package br.com.jzsports.tournament_control.model.entity;

import br.com.jzsports.tournament_control.model.e.EMatchStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private EMatchStatus status;

    @ManyToOne
    @JoinColumn(name = "championship_id")
    private Championship championship;

    @ManyToOne
    @JoinColumn(name = "phase_id", nullable = false)
    private Phase phase;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchParticipant> participants;

    public Team getCancellingTeam() {
        return participants.stream()
                .filter(MatchParticipant::isCancelled)
                .map(MatchParticipant::getTeam)
                .findFirst()
                .orElse(null);
    }

    public Player getCancellingPlayer() {
        return participants.stream().filter(MatchParticipant::isCancelled).map(MatchParticipant::getPlayer).findFirst().orElse(null);
    }

}
