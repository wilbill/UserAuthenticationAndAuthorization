package com.security.userauthenticationandauthorization.service.impl;

import com.security.userauthenticationandauthorization.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    private static String SECRET_KEY="cdd4548a67efabd04267e6d49cc9e27eaf385fbe9e6d941dfc3121eaff8bca01";
    public String extractUserName(String token){
        //return null;
        return extractClaim(token, Claims::getSubject);
    }

    //mtd to extract a single claim
    public <T> T extractClaim(String token,  Function<Claims, T> claimsResolver ){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    //code to extract all claims
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        //return null;
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }
    //for security, min signing key is 256
    //allkeysgenerator.com failed
    //https://seanwasere.com/generate-random-hex/

}
