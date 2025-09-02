package br.com.jzsports.tournament_control.model.e;

import lombok.Getter;

@Getter
public enum EChampionship {

    FPS("FPS"),
    FIGHT("FIGHT"),
    RACING("RACING"),
    SPORT("SPORT"),
    MOBA("MOBA");

    private final String description;

    EChampionship(String description){
        this.description = description;
    }
}
