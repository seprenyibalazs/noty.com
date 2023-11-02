package com.noty.web.services.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EntityPermission {

    private long userId;
    private boolean canRead;

}
