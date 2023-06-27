package com.rmnnorbert.InquireNet.dao.model.user;

import com.rmnnorbert.InquireNet.dao.model.user.data.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
public class User implements UserDetails {
    private long id;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String username;
    private String password;
    private LocalDateTime registration_date;
    public User(long id, Role role, String username, String password, LocalDateTime registration_date) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.password = password;
        this.registration_date = registration_date;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
