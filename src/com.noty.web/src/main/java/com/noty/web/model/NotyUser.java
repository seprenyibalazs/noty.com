package com.noty.web.model;

import com.noty.web.entities.User;
import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Data;
import org.aspectj.weaver.ast.Not;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Data
public class NotyUser {

    private long id;
    private String email;

    public static NotyUser fromClaims(Claims claims) {
        int id = Integer.parseInt(claims.getId());

        return NotyUser.builder()
                .email(claims.getSubject())
                .id(id)
                .build();

    }

    public static NotyUser fromUser(User user) {
        return NotyUser.builder()
                .email(user.getEmail())
                .id(user.getId())
                .build();
    }
}
