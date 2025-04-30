package com.logonedigital.pilot.shared.domain;

import java.util.Base64;
import java.util.UUID;
import lombok.NonNull;

public record PublicId(@NonNull String value) {
    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

    public PublicId {
        if (value.isBlank()) {
            throw new IllegalArgumentException("PublicId cannot be blank");
        }
    }

    public static PublicId fromUuid(UUID uuid) {
        return new PublicId(encoder.encodeToString(
                uuid.toString().getBytes()
        ));
    }

    public static UUID toUuid(@NonNull String value) {
        return UUID.fromString(
                new String(Base64.getUrlDecoder().decode(value))
        );
    }
}
