package com.noty.web.services.security;

import com.noty.web.framework.model.Validatable;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class Credentials implements Validatable {

    private String email;
    private String password;

    @Override
    public boolean isValid() {
        return StringUtils.hasText(email)
                && StringUtils.hasText(password);
    }
}
