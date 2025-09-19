package br.com.jzsports.tournament_control.model.e;

import lombok.Getter;

@Getter
public enum EChampionshipStatus {
    IN_PROGRESS("In Progress"),
    FINISHED("Finished"),
    CANCELLED("Cancelled");

    private final String description;

    EChampionshipStatus(String description) {
        this.description = description;
    }
}
