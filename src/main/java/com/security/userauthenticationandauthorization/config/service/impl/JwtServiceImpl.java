package com.security.userauthenticationandauthorization.config.service.impl;

import com.security.userauthenticationandauthorization.config.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
    //below mtd is used to generate token without extraToken
    public String generateToken(UserDetails userDetails){
       return generateToken(new HashMap<>(), userDetails);
    }

    //mtd to generate token, but uses extraClaims
    public String generateToken(
        Map<String, Object> extraClaims,
                UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact(); //generates toekn
    }

    //mtd to validate a token
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token) {
        return extraExpiration(token).before(new Date());//be4 todays date
    }

    private Date extraExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //for security, min signing key is 256
    //allkeysgenerator.com failed
    //https://seanwasere.com/generate-random-hex/
}
