package br.com.jzsports.tournament_control.model.entity;

import br.com.jzsports.tournament_control.model.e.EChampionshipStatus;
import br.com.jzsports.tournament_control.model.e.ETypeChampionship;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Championship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String championshipName;

    @Enumerated(EnumType.STRING)
    private ETypeChampionship championshipType;

    @Enumerated(EnumType.STRING)
    private EChampionshipStatus championshipStatus;

    @ManyToOne
    @JoinColumn(name = "created_by_player_id")
    private Player createdBy;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdAt;

    private LocalDate startDate;
    private LocalDate endDate;

    // 🔹 Times que participam do campeonato
    @OneToMany(mappedBy = "championship", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ChampionshipParticipant> participants;

    // 🔹 Fases do campeonato
    @OneToMany(mappedBy = "championship", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Phase> phases;
}
