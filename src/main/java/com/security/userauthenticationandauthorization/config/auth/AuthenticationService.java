package com.security.userauthenticationandauthorization.config.auth;

import com.security.userauthenticationandauthorization.repository.UserRepository;
import com.security.userauthenticationandauthorization.config.service.impl.JwtServiceImpl;
import com.security.userauthenticationandauthorization.token.Token;
import com.security.userauthenticationandauthorization.token.TokenRepository;
import com.security.userauthenticationandauthorization.token.TokenType;
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
    private final TokenRepository tokenRepository;
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
                .role(registerRequest.getRole()) //.role(Role.USER), I changed it coz of role field put in register
                .build();

        var savedUser = userRepository.save(user); //added left part for logout uses, new content
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken); //added logout

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
        revokeAllUserTokens(user); //be4 saving token, we need to revoke it==>added for logout purpose
        saveUserToken(user,jwtToken); //added later
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    //revokes all valid tokens by user, its part of the logout
    private void revokeAllUserTokens(User user){
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(t ->{
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) { //param name
        var token = Token //added for logout uses
                .builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false) //we set it to false coz wen its generated, its not expired, its not revoked
                .expired(false)
                .build();
        tokenRepository.save(token);
    }
}
