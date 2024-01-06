package com.security.userauthenticationandauthorization.config.auth;

import com.security.userauthenticationandauthorization.repository.UserRepository;
import com.security.userauthenticationandauthorization.config.service.impl.JwtServiceImpl;
import com.security.userauthenticationandauthorization.user.Role;
import com.security.userauthenticationandauthorization.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//class to implement the register and authenticate
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    //injecting the beans which will be used
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

    //allows us create a user, save in db, returns a generated token
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
                .firstName(registerRequest.getFirstname())
                .lastName(registerRequest.getLastname())
                .email(registerRequest.getEmail()) //added this line later on, wasnt there
                .password(passwordEncoder.encode(registerRequest.getPassword())) //this needs to be encoded be4 DB
                .role(Role.USER)
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        //to authenticate user, we call this manager
        //an execption is thrown if uname and pswd not correct
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        // otherwise, creating a user, if above is correct
        var user  = userRepository.findUsersByEmail(authenticationRequest.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
