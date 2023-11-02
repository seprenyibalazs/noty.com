package com.noty.web.services.security;

import com.noty.web.NotyAuthorizationException;
import com.noty.web.NotyException;
import com.noty.web.NotyForbiddenException;
import com.noty.web.entities.NotyList;
import com.noty.web.entities.User;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotyImpersonation implements UserDetails {

    private long id;
    private String email;

    public static NotyImpersonation fromUser(User user) {
        return NotyImpersonation.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    public static NotyImpersonation fromClaims(Claims claims) {
        long id = Long.parseLong(claims.getId());

        return NotyImpersonation.builder()
                .id(id)
                .email(claims.getSubject())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasReadAccess(SecuredEntity entity) {
        return Arrays.stream(entity.getAcl())
                .anyMatch(p -> p.getUserId() == id && p.isCanRead());
    }

    public boolean hasWriteAccess(SecuredEntity entity) {
        return entity instanceof OwnedEntity
                ? ((OwnedEntity) entity).getOwnerId() == getId()
                : hasReadAccess(entity);
    }

    public void assertReadAccess(SecuredEntity entity) throws NotyException {
        if (!hasReadAccess(entity))
            throw new NotyForbiddenException("You do not have access to this information.");
    }

    public void assertWriteAccess(SecuredEntity entity) throws NotyException {
        if (!hasWriteAccess(entity))
            throw new NotyForbiddenException("You do not have access to this information.");
    }
}
