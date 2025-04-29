package com.logonedigital.pilot.user.domain.aggregate;

import com.logonedigital.pilot.user.domain.enums.Role;
import com.logonedigital.pilot.user.domain.vo.Email;
import com.logonedigital.pilot.user.domain.vo.FullName;
import com.logonedigital.pilot.user.domain.vo.UserId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@Builder
public class User {
    private Long dbId;
    private UserId publicId;
    private FullName fullName;
    private Email email;
    private String password;
    private boolean enabled;
    private boolean accountLocked;
    private Set<Role> roles;
}
