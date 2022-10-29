package dev.layewane.demosecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class RobotAuthentication implements Authentication {

    private Collection<SimpleGrantedAuthority> authorities;
    private boolean authenticated;

    private Object password;

    private RobotAuthentication(Collection<SimpleGrantedAuthority> authorities, Object password) {
        this.authorities = authorities;
        this.authenticated = !authorities.isEmpty();
        this.password = password;
    }

    public static RobotAuthentication token(String password) {
        return new RobotAuthentication(Collections.emptyList(), password);
    }

    public static RobotAuthentication authenticated() {
        return new RobotAuthentication(Collections.singleton(
                new SimpleGrantedAuthority("ROLE_ROBOT")
        ), null);
    }

    @Override
    public String getName() {
        return "Mr Robot 🧞‍";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getPrincipal() {
        return "Mr Robot 🧞‍";
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public boolean equals(Object another) {
        return false;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
