package com.security.userauthenticationandauthorization.config.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest
    ){
        //return ResponseEntity.ok(authenticationService.register(registerRequest));
        return new ResponseEntity<AuthenticationResponse>(authenticationService.register(registerRequest), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ){
        //return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest)); same as below but im used to one below
        return new ResponseEntity<AuthenticationResponse>(authenticationService.authenticate(authenticationRequest),HttpStatus.OK);
    }
}
