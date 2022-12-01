package com.openclassrooms.paymybuddy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                    .antMatchers(HttpMethod.GET, "/login").permitAll()
                    .antMatchers(HttpMethod.GET, "/signup").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/authentication/add").permitAll()
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .defaultSuccessUrl("/home")
                .and()
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
