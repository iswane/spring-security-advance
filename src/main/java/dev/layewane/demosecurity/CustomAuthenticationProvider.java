package dev.layewane.demosecurity;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var auth = (UsernamePasswordAuthenticationToken) authentication;
        if ("laye".equals(auth.getPrincipal())) {
            return new UsernamePasswordAuthenticationToken(
                    "laye",
                    null,
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
