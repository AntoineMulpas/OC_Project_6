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
                .cors().disable()
                .authorizeHttpRequests()
                    .antMatchers("/css/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/login", "/signup", "/login-error.html").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/authentication/add", "/signup").permitAll()
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/home")
                .failureUrl("/login-error.html")
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe()
                .key("AGK123AZERTY")
                .tokenValiditySeconds(86400)
                .and()
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
