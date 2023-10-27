package com.noty.web.filters;

import com.noty.web.components.JwtUtil;
import com.noty.web.services.security.JwtUserDetails;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;

    private String getJwtToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token))
            return null;

        return token.startsWith("Bearer")
                ? token.substring("Bearer ".length())
                : null;

    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        applyJwtAuthorization(request);

        filterChain.doFilter(request, response);
    }

    private void applyJwtAuthorization(HttpServletRequest request) {
        String jwt = getJwtToken(request);
        if (jwt == null) {
            logger.debug("JWT bearer token is empty for the request.");
            return;
        }

        Claims claims = jwtUtil.decode(jwt);
        JwtUserDetails user = new JwtUserDetails(claims);

        WebAuthenticationDetails details = new WebAuthenticationDetailsSource()
                .buildDetails(request);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user,
                null,
                List.of(new SimpleGrantedAuthority("user"))
        );
        authentication.setDetails(details);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
