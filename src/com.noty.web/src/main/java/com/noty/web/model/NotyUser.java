package com.noty.web.model;

import com.noty.web.entities.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Data
public class NotyUser {

    private long id;
    private String email;

    public static NotyUser fromUser(User user) {
        return NotyUser.builder()
                .email(user.getEmail())
                .id(user.getId())
                .build();
    }
}
