package com.example.client.controller;

import com.example.sdk.service.JwtTokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;

@RestController
public class ClientController {
   private String secret = "javatechie";

   @Autowired
   private JwtTokenProvider jwtTokenProvider;

   @PostMapping("/api/client")
   public void test() {
      RestTemplate restTemplate = new RestTemplate();
      HttpHeaders requestHeaders = new HttpHeaders();
      requestHeaders.add("Authorization", "Bearer " + jwtTokenProvider.generateToken("chaabinet"));
      HttpEntity<?> httpEntity = new HttpEntity<Object>(requestHeaders);
      ResponseEntity<String> responseEntity = restTemplate.exchange(
            "http://localhost:9091/api/server",
            HttpMethod.POST,
            httpEntity,
            String.class
      );
   }

}
