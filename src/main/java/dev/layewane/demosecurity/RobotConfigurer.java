package dev.layewane.demosecurity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

public class RobotConfigurer extends AbstractHttpConfigurer<RobotConfigurer, HttpSecurity> {

    private final List<String> passwords = new ArrayList<>();

    public RobotConfigurer password(String password) {
        this.passwords.add(password);
        return this;
    }



    @Override
    public void init(HttpSecurity http) throws Exception {
        var provider = new RobotAuthenticationProvider(passwords);
        http.authenticationProvider(provider);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        var manager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(
             new RobotFilter(manager),
             UsernamePasswordAuthenticationFilter.class
        );
    }
}
