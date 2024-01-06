package com.security.userauthenticationandauthorization.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
//these were adde later on
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutHandler;

//class Binding with filter
@Configuration //makes a class configuration class
@EnableWebSecurity //this works together with above in spring 3
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter; //this is mine
    private final AuthenticationProvider authenticationProvider; //This is builtin
    private final LogoutHandler logoutHandler;


    //Bean responsible for configuring all http security
    @Bean //exception is coz build might throw an exception
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authConfig -> {
                            authConfig
                            .requestMatchers("/actuator/**").hasAuthority("ADMIN")
                                    .requestMatchers("/api/v1/auth/**", "/static/**", "/auth/login").permitAll() //Me-added api/v1/auth
                            .anyRequest().authenticated();
                        })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/v1/auth/logout") //this is the url neede to execute the logout handler
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(((request, response, authentication) ->
                        SecurityContextHolder.clearContext()));//wht to do wen one logs out, we also need a url

                return http.build();

//        http
//                .csrf().disable() //start by diasbling csrf
//                .authorizeHttpRequests()
//                .requestMatchers("/api/v1/auth/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
    }
}
