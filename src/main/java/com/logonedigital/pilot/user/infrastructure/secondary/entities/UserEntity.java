package com.logonedigital.pilot.user.infrastructure.secondary.entities;

import com.logonedigital.pilot.user.domain.enums.Role;
import com.logonedigital.pilot.user.domain.aggregate.User;
import com.logonedigital.pilot.user.domain.vo.Email;
import com.logonedigital.pilot.user.domain.vo.FullName;
import com.logonedigital.pilot.user.domain.vo.UserId;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_user")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements UserDetails, Principal {

    @Id
    @GeneratedValue
    private Long dbId;
    @Column(name = "publicId", unique = true)
    private UUID publicId;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean enabled;
    private boolean accountLocked;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }
    public static UserDetails from(User user) {
        return UserEntity.builder()
                .dbId(user.getDbId())
                .firstname(user.getFullName().firstName())
                .lastname(user.getFullName().lastName())
                .email(user.getEmail().value())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .accountLocked(user.isAccountLocked())
                .roles(user.getRoles())
                .build();
    }


    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    @Override
    public String getName() {
        return this.email;
    }

    public static User toDomain(UserEntity entity){
            return User.builder()
                    .dbId(entity.getDbId())
                    .publicId(new UserId(entity.getPublicId()))
                    .fullName(new FullName(entity.getFirstname(), entity.getLastname()))
                    .email(new Email(entity.getEmail()))
                    .password(entity.getPassword())
                    .enabled(entity.isEnabled())
                    .accountLocked(entity.isAccountLocked())
                    .roles(entity.getRoles())
                    .build();
    }
}
