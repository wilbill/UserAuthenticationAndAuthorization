package com.security.userauthenticationandauthorization.config;

import com.security.userauthenticationandauthorization.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    @Override //build-in interface methods
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        final String authenticationHeader = request.getHeader("Authorization");
        final String jwt;
        if(authenticationHeader==null || !authenticationHeader.startsWith("Bearer ")){
            return; //we shdn't continue
        }
        //now extracting the token, without Bearer keyword
        jwt = authenticationHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if(storedToken!=null){
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }


    }
}
