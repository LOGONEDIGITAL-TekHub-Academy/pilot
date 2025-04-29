package com.logonedigital.pilot.user.infrastructure.secondary.repositories;

import com.logonedigital.pilot.user.domain.aggregate.User;
import com.logonedigital.pilot.user.domain.repository.UserRepository;
import com.logonedigital.pilot.user.infrastructure.secondary.entities.UserEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User findByEmail(String userEmail) {
        UserEntity userEntity = jpaUserRepository.findByEmail(userEmail)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        assert userEntity != null;
        return UserEntity.toDomain(userEntity);
    }
}
