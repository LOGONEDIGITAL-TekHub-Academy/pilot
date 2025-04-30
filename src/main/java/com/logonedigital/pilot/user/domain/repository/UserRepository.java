package com.logonedigital.pilot.user.domain.repository;

import com.logonedigital.pilot.user.domain.aggregate.User;


public interface UserRepository {
    User findByEmail(String userEmail);
}
