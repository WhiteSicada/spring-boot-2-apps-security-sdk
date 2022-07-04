package com.example.server.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

   @Value("${application.one.username}")
   private String usernameOne;

   @Value("${application.two.username}")
   private String usernameTwo;


   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      if (username.equals(usernameOne)) {
         return new org.springframework.security.core.userdetails.User(usernameOne, "V2zC#zHBRq_dYU_Ahpe3uhttEW", new ArrayList<>());
      } else if (username.equals(usernameTwo)) {
         return new org.springframework.security.core.userdetails.User(usernameTwo, "uxDKPh+&a3L9D!m6b_*V?KjM+&", new ArrayList<>());
      } else {
         throw new UsernameNotFoundException("User not found with username: " + username);
      }
   }
}