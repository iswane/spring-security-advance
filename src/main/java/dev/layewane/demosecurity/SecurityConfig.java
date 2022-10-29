package dev.layewane.demosecurity;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationEventPublisher publisher) throws Exception {

        http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationEventPublisher(publisher);

//        var manager = new ProviderManager(new RobotAuthenticationProvider("beep-boop", "boop-beep"));
//        manager.setAuthenticationEventPublisher(publisher);
//        var configurer = new RobotConfigurer()
//                .passwords("beep-boop")
//                .passwords("boop-beep");

        // @formatter:off
        return http
                .authenticationProvider(new CustomAuthenticationProvider())
                .authorizeRequests()
                .antMatchers("/private").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .oauth2Login()
                .and()
//                .apply(configurer)
                .apply(new RobotConfigurer())
                .password("beep-boop")
                .password("boop-beep")
                .and()
//                .addFilterBefore(new RobotFilter(manager), UsernamePasswordAuthenticationFilter.class)
                .build();
        // @formatter:on
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                new User("user", "{noop}password", Collections.emptyList())
        );
    }

    @Bean
    public ApplicationListener<AuthenticationSuccessEvent> onSuccess() {
        return (event -> {
            String authClassName = event.getAuthentication().getClass().getSimpleName();
            String username = event.getAuthentication().getName();
            System.out.println("👏 Login Successful " + authClassName + " - " + username);
        });
    }
}
