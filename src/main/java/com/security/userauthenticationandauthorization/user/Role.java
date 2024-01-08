package com.security.userauthenticationandauthorization.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.security.userauthenticationandauthorization.user.Permission.*;
@RequiredArgsConstructor
public enum Role { //Each role can have multiple permissions
    USER(Collections.emptySet()), //assume user has no permissions
    ADMIN(Set.of(ADMIN_READ, ADMIN_CREATE, ADMIN_UPDATE, ADMIN_DELETE,
                    MANAGER_READ, MANAGER_CREATE, MANAGER_UPDATE, MANAGER_DELETE)),
    MANAGER(Set.of(MANAGER_READ, MANAGER_CREATE, MANAGER_UPDATE, MANAGER_DELETE));

    @Getter
    private final Set<Permission> permissions; //used set to avoid dups, e.g admin_read is twice, its accepted once

    //method with obj simplegrantedauthority
    public List<SimpleGrantedAuthority>getAuthorities(){
        var authorities = getPermissions()
                        .stream()
                        //shd be permission.getPermission() not permission.getName()
                        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                        .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
