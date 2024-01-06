package com.security.userauthenticationandauthorization.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor //will do auto field injection without the keyword
@RequestMapping("/api/v1/users") //secured end point, so, we will need to use principal object to get connected users otherwise we cant
public class UserController {
    private final UserService userService;

    //End points for password change
    @PatchMapping
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, Principal connectedUser){
        userService.changePassword(changePasswordRequest, connectedUser);
        //return new ResponseEntity<ChangePasswordRequest>(HttpStatus.OK); --> this specifies the response body type
        return ResponseEntity.ok().build(); //ok gives 200, build creates an empty response body, i can use accepted instead of ok also.
    }

}
