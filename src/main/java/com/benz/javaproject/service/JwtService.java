package com.benz.javaproject.service;

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

//payload contains the claims username


@Service
public class JwtService {

    private final String secret = "6Jkr5mcXPHb0C4gRwGHpBN0MYHBI/aV5ib3DgDzTNFUtGGRjq1AU17i1S2RUqg/V";

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    //someone want to generate a token but not wants to clarify extraClaims:
    public String generateToken(UserDetails userDetails){
        return generateToken(userDetails,new HashMap<>());
    }

    //validate the token
    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpire(token));

    }

    private boolean isTokenExpire(String token) {
        return extractExpirationTime(token).before(new Date());
    }

    private Date extractExpirationTime(String token) {
        return extractClaim(token,Claims::getExpiration);
    }


    public String generateToken(UserDetails userDetails, Map<String,Object> extraClaims){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimDecoder){
        Claims claim = extractAllClaims(token);
        return claimDecoder.apply(claim);

    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keybyte = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keybyte);
    }


}
