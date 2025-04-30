package com.logonedigital.pilot.user.domain.service;

import com.logonedigital.pilot.user.domain.aggregate.User;
import com.logonedigital.pilot.user.domain.vo.Email;
import com.logonedigital.pilot.user.domain.vo.FullName;
import com.logonedigital.pilot.user.domain.vo.UserId;

public interface UserService {
    User registerStudent(FullName fullName, Email email, String matricule);
    User registerMentor(FullName fullName, Email email, String expertise);
    void promoteToMentor(UserId userId, String expertise);
}
