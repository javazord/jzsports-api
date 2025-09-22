package br.com.jzsports.tournament_control.model.entity;

import br.com.jzsports.tournament_control.model.dto.team.TeamDTO;
import br.com.jzsports.tournament_control.model.dto.team.TeamRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String teamName;

    @Column
    private String photoURL;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdAt;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<TeamMembership> memberships = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ChampionshipParticipant> championshipParticipations;

    public void addMembership(Player player) {
        TeamMembership membership = new TeamMembership();
        membership.setPlayer(player);
        membership.setTeam(this);
        this.memberships.add(membership);
    }

    public void setTeamNameAndPhoto(TeamRequestDTO dto) {
        this.teamName = dto.getTeamName();
        this.photoURL = dto.getPhotoURL();
    }

}
