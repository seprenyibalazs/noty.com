package com.noty.web.services.security;

import com.noty.web.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotyImpersonation {

    private long id;
    private String email;

    public static NotyImpersonation fromUser(User user) {
        return NotyImpersonation.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

}
