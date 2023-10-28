package com.noty.web.controllers.api.model.response;

import com.noty.web.services.security.NotyImpersonation;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NotyUserResponse {

    private long id;
    private String email;

    public static NotyUserResponse fromImpersonation(NotyImpersonation impersonation) {
        return NotyUserResponse.builder()
                .email(impersonation.getEmail())
                .id(impersonation.getId())
                .build();
    }
}
