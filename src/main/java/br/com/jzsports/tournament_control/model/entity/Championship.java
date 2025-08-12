package br.com.jzsports.tournament_control.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long id;
    @Column(unique = true)
    private String name;
    @Column
    private String type;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @OneToMany(mappedBy = "championship")
    private List<Team> teams;
    @OneToMany(mappedBy = "championship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Match> matches;

}
