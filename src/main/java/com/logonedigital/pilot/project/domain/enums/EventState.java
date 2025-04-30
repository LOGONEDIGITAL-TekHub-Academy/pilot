package com.logonedigital.pilot.project.domain.enums;

import lombok.Getter;

@Getter
public enum EventState {
    ON_CREATE("On-Create"),
    ON_UPDATE("On-Update"),
    ON_DELETE("On-Delete"),
    ;

    EventState(String state) {
        assert state != null && !state.trim().isEmpty() : "EvenState cannot be null or empty";
    }

}
