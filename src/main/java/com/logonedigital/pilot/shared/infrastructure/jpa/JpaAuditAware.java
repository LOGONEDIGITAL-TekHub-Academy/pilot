package com.logonedigital.pilot.shared.infrastructure.jpa;

import com.logonedigital.pilot.user.infrastructure.secondary.entities.UserEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class JpaAuditAware implements AuditorAware<Long> {

    @Override
    @NonNull
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                    authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        UserEntity userPrincipal = (UserEntity) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getDbId());
    }
}
