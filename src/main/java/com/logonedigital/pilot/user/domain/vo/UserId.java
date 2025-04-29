package com.logonedigital.pilot.user.domain.vo;

import java.util.UUID;
import lombok.NonNull;

public record UserId(@NonNull UUID value) {
    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    public static UserId fromString(String uuid) {
        return new UserId(UUID.fromString(uuid));
    }
}