package com.logonedigital.pilot.user.domain.vo;

import lombok.NonNull;

public record FullName(
        @NonNull String firstName,
        @NonNull String lastName
) {
    public FullName {
        if (firstName.isBlank() || lastName.isBlank()) {
            throw new IllegalArgumentException(
                    "First and last names cannot be empty"
            );
        }
        firstName = firstName.strip();
        lastName = lastName.strip();
    }

    @Override
    public @NonNull String firstName() {
        return firstName;
    }

    @Override
    public @NonNull String lastName() {
        return lastName;
    }

    @NonNull
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}