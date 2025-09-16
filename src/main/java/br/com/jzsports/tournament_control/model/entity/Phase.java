package br.com.jzsports.tournament_control.model.entity;

import jakarta.persistence.*;
import lombok.*;
import br.com.jzsports.tournament_control.model.e.ETypePhase;

import java.io.Serializable;
import java.util.List;

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
    @Column(nullable = false)
    private ETypePhase phase;

    @ManyToOne
    @JoinColumn(name = "championship_id", nullable = false)
    private Championship championship;

    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Match> matchesList;

}
