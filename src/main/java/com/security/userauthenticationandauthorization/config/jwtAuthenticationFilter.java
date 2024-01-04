package com.security.userauthenticationandauthorization.config;

import com.security.userauthenticationandauthorization.service.impl.JwtServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull; //shd be from spring framework
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//This is the filter class.
@Component
@RequiredArgsConstructor //creates a constr using and fields using final
public class jwtAuthenticationFilter extends OncePerRequestFilter {

   // @Autowired, why it works?
    private final JwtServiceImpl jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        //final variable cannot be reassigned once it has been initialized
        //when call made, we pass jwt authentication token within header/Bearer
        //java code gets the (token) from "Authorization" header from an HTTP request. The value is then assigned to the authenticationHeader variable
        final String authenticationHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if(authenticationHeader==null || !authenticationHeader.startsWith("Bearer ")){
            //if above condition, pass req and res to next filter
            filterChain.doFilter(request, response);
            return; //we shdn't continue
        }
        //now extracting the token, without Bearer keyword
        jwt = authenticationHeader.substring(7);
        //we extract userEmail from JWT Token (just know userName is our email )
        userEmail = jwtService.extractUserName(jwt);


    }
}

