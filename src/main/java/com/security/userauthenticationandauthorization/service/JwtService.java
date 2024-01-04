package com.security.userauthenticationandauthorization.service;

public interface JwtService {
    String extractUserName(String jwt);

}
