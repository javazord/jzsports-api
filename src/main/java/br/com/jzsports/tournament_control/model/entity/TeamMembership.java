package br.com.jzsports.tournament_control.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TeamMembership implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDate joinedAt;

    @Column
    private LocalDate leftAt;

}
