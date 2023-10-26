package com.noty.web.model;

import com.noty.web.entities.User;
import lombok.Builder;
import lombok.Data;

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
