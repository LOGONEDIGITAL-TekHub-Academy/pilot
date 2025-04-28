package com.logonedigital.pilot.project.domain.vo;

import java.util.List;

public record ProjectStatus(String value) {
    public static final String CREATED = "CREATED";
    public static final String IN_PROGRESS = "IN_PROGRESS";
    public static final String VALIDATED = "VALIDATED";
    public static final String COMPLETED = "COMPLETED";

    public ProjectStatus {
        if(!List.of(CREATED, IN_PROGRESS,VALIDATED, COMPLETED).contains(value)) {
            throw new IllegalArgumentException("Invalid status");
        }
    }
}