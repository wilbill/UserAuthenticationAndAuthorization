package com.security.userauthenticationandauthorization.config;

import com.security.userauthenticationandauthorization.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;
    //private PasswordEncoder passwordEncoder;
    //private PasswordEncoder passwordEncoder;

    @Bean //means we will use external class, and also Bean shd be public, its an interface from spring Security
    public UserDetailsService userDetailsService() {

        //what is returned is also an interface from spring security
        return (UserDetailsService) username -> userRepository.findUsersByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User Not found"));

    }
    //this is dao fetch userDetails, encode pswd etc,
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //we tell auth provider which userDetails to use inorder to fetch our user-info
        authenticationProvider.setUserDetailsService(userDetailsService());
        //w/c password encoder
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    //Bean to manage the authentication
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
