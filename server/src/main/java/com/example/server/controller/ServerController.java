package com.example.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

   @Value("${application.one.username}")
   private String usernameOne;

   @Value("${application.two.username}")
   private String usernameTwo;

   @PostMapping("/api/server")
   public void test() {
      System.out.println("usernameOne");
   }
}
