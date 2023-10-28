package com.noty.web.components.implementations;

import com.noty.web.components.DateTime;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DefaultDateTimeProvider implements DateTime {
    @Override
    public Date now() {
        return new Date();
    }
}
