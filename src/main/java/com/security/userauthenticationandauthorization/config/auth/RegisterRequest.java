package com.security.userauthenticationandauthorization.config.auth;

import com.security.userauthenticationandauthorization.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role; //think of a way of handling this role...i dont know if its wise for it to be here,
}
