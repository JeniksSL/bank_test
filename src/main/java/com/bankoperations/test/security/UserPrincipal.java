package com.bankoperations.test.security;


import com.bankoperations.test.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class UserPrincipal implements UserDetails {

    private final String login;
    private final Long userId;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean isEnabled;

    public static UserPrincipal create(final User user) {
        return new UserPrincipal(user.getLogin(), user.getId(), user.getPassword(), List.of(), user.getIsEnabled());
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
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
        return isEnabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getUserId() {
        return userId;
    }
}
