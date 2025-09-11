package br.com.jzsports.tournament_control.model.entity;

import br.com.jzsports.tournament_control.model.e.EChampionshipStatus;
import br.com.jzsports.tournament_control.model.e.ETypeChampionship;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Column
    @Enumerated(EnumType.STRING)
    private ETypeChampionship  championshipType;

    @Column
    @Enumerated(EnumType.STRING)
    private EChampionshipStatus championshipStatus;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @ManyToMany
    @JoinTable(
            name = "championship_teams",
            joinColumns = @JoinColumn(name = "championship_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teamsList;

    @OneToMany(mappedBy = "championship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Match> matchesList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_player_id", nullable = false)
    private Player createdBy;

}
