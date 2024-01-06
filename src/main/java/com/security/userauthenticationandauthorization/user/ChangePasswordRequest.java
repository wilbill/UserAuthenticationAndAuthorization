package com.security.userauthenticationandauthorization.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//to change password, user has to give us the old password
@Getter
@Setter
@Builder
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;
}
