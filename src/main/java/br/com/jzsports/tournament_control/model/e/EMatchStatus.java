package br.com.jzsports.tournament_control.model.e;

import lombok.Getter;

@Getter
public enum EMatchStatus {
    IN_PROGRESS("In Progress"),
    FINISHED("Finished"),
    CANCELLED("Cancelled"),;

    private final String description;

    EMatchStatus(String description) {
        this.description = description;
    }

}
