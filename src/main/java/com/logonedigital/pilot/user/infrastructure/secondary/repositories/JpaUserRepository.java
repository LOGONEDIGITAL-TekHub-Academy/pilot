package com.logonedigital.pilot.user.infrastructure.secondary.repositories;

import com.logonedigital.pilot.user.infrastructure.secondary.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
