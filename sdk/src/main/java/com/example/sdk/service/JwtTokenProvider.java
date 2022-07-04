package com.example.sdk.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class JwtTokenProvider {

   @Value("${security.jwt.secret-key}")
   private String secretKey;

   @Value("${security.jwt.expire-length}")
   private long validityInMilliseconds;

   @PostConstruct
   protected void init() {
      secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
   }

   public String generateToken(String username) {
      return Jwts
            .builder()
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
   }
}