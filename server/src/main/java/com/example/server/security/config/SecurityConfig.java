package com.example.server.security.config;

import com.example.server.security.filter.JwtFilter;
import com.example.server.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   private CustomUserDetailsService userDetailsService;

   @Autowired
   private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

   @Autowired
   private JwtFilter jwtFilter;

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      // TODO configure authentication manager
      // configure AuthenticationManager so that it knows from where to load
      // user for matching credentials
      // Use BCryptPasswordEncoder
      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      // Along with the authentication provider, we need to configure an authentication
      // manager with the correct password-encoding schema that will be used for credentials
      // verification. For this, we need to expose the preferred implementation of the PasswordEncoder interface as a bean.
      return new BCryptPasswordEncoder();
   }

   @Bean
   @Override
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http
            // Enable CORS and disable CSRF
            .cors().and().csrf().disable()

            // Set permissions on endpoints
            .authorizeRequests()
            // dont authenticate this particular request
//            .antMatchers("/api/server").permitAll()
            // all other requests need to be authenticated
            .anyRequest().authenticated().and()
            // make sure we use stateless session; session won't be used to
            // store user's state.
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

      // Add JWT token filter
      // We’re doing this because we need access to the user identity at this point to perform
      // authentication/authorization, and its extraction happens inside the JWT token filter
      // based on the provided JWT token
      http.addFilterBefore(
            jwtFilter,
            UsernamePasswordAuthenticationFilter.class
      );
   }
}
