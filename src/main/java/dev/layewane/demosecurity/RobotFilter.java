package dev.layewane.demosecurity;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class RobotFilter extends OncePerRequestFilter {
    private final static String HEADER_NAME = "x-robot-password";
    private final AuthenticationManager authenticationManager;

    public RobotFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!Collections.list(request.getHeaderNames()).contains(HEADER_NAME)) {
            filterChain.doFilter(request, response);
            return; // permet de sortir du filtre
        }

        String password = request.getHeader(HEADER_NAME);
        var token = RobotAuthentication.token(password);
        Authentication authentication = authenticationManager.authenticate(token);

        if (authentication != null) {
            var context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("utf-8");
            response.getWriter().println("You are not mister Robot 👎");
        }
    }
}
