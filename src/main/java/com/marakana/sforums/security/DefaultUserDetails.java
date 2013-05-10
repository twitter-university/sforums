
package com.marakana.sforums.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.marakana.sforums.domain.User;

public class DefaultUserDetails implements UserDetails {

    private static final long serialVersionUID = 1187218242956480188L;

    private static final GrantedAuthority USER = new SimpleGrantedAuthority("ROLE_USER");

    private static final GrantedAuthority ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");

    private final User user;

    public DefaultUserDetails(User user) {
        if (user == null) {
            throw new NullPointerException("User must not be null");
        }
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(2);
        authorities.add(USER);
        if (this.getUser().isAdmin()) {
            authorities.add(ADMIN);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPasswordDigest();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
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
        return this.user.isEnabled();
    }

    @Override
    public String toString() {
        return this.user + " user details";
    }
}
