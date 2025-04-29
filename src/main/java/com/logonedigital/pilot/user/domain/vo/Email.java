package com.logonedigital.pilot.user.domain.vo;

import com.logonedigital.pilot.shared.domain.RegexPatterns;
import lombok.NonNull;

public record Email(@NonNull String value) {
    public Email {
        if (!value.matches(RegexPatterns.EMAIL)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}