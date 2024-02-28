package com.test.security;

import com.test.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * CustomUserDetail class implements the UserDetails interface for custom user details in Spring Security.
 * It holds information about the authenticated user, including username, password, and authorities.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomUserDetail implements UserDetails {

    /**
     * The user entity associated with the authenticated user.
     */
    private User user;

    /**
     * Collection of GrantedAuthority representing the authorities of a user.
     * These authorities typically include roles and permissions assigned to the user.
     */
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
