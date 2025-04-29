package com.logonedigital.pilot.project.domain.vo;

public record ProjectTitle(String value) {

    public ProjectTitle {
        if (value == null) {
            throw new IllegalArgumentException("Title value cannot be null");
        }
        if (value.length() < 3 || value.length() > 256) {
            throw new IllegalArgumentException("Title length must be between 3 and 256 characters");
        }
    }
}