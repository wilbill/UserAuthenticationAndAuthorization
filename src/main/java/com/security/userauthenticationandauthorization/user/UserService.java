package com.security.userauthenticationandauthorization.user;

import com.security.userauthenticationandauthorization.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    //we need to know we are changing password for w/c user
    public void changePassword(ChangePasswordRequest changePasswordRequest, Principal connectedUser) {
        var user = (User)((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal(); //we cast the obj to getPrincipal

        //checking if the current password is correct
        if( !passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())){
            throw new IllegalStateException("Wrong Password"); //check exception handling please, dont use this
        }
        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmationPassword())){
            throw new IllegalStateException("Password dont match"); //check exception handling please, dont use this
        }
        //update password and save user
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);


    }
}
