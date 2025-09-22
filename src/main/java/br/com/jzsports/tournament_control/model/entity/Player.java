package br.com.jzsports.tournament_control.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String color;

    @Column
    private String photoURL;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<TeamMembership> memberships;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ChampionshipParticipant> championshipParticipations;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Championship> championshipsCreated;
}
