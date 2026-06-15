package com.sudoku.game.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.sudoku.game.service.MyUserDetailsService;

@Configuration
public class SecurityConfig {

    @Autowired
    MyUserDetailsService service;

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(service);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authenticationProvider(authProvider())
            .authorizeHttpRequests((auth) -> {
            auth.requestMatchers("/","/signup", "/leaderboard").permitAll();
            auth.anyRequest().authenticated();
        })
        .formLogin(form -> form
            .loginPage("/login")
            .permitAll()
        )
        .logout(logout -> logout
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login")
        .permitAll()
        );

        return http.build();
    }
}
