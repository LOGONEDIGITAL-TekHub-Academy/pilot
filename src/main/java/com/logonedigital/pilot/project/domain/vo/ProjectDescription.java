package com.logonedigital.pilot.project.domain.vo;

public record ProjectDescription(String value) {

    public ProjectDescription {
        if (value == null) {
            throw new IllegalArgumentException("Description value cannot be null");
        }
        if (value.length() < 3 || value.length() > 256) {
            throw new IllegalArgumentException("Description length must be between 3 and 256 characters");
        }
    }
}