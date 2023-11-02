package com.noty.web.services.security;

public interface SecuredEntity {

    EntityPermission[] getAcl();

}
