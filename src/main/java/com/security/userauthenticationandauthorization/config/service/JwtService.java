package com.security.userauthenticationandauthorization.config.service;

public interface JwtService {
    String extractUserName(String jwt);

}
