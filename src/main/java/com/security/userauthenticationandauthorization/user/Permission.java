package com.security.userauthenticationandauthorization.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public enum Permission {
    //this enum different permissions for different resources, u can create as many as u want
    ADMIN_READ("admin:read"), //FOR GET-MAPPING
    ADMIN_UPDATE("admin:update"), //FOR PUT-MAPPING
    ADMIN_CREATE("admin:create"),  //FOR POST-MAPPING
    ADMIN_DELETE("admin:delete"),
    MANAGER_READ("management:read"), //FOR GET-MAPPING
    MANAGER_UPDATE("management:update"), //FOR PUT-MAPPING
    MANAGER_CREATE("management:create"),  //FOR POST-MAPPING
    MANAGER_DELETE("management:delete");

    @Getter
    private final String permission; //Variable 'permission' might not have been initialized,==> needs a constr.

}
