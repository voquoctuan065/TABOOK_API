package com.tacm.tabooksapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
public class SecurityConfig {

    @SuppressWarnings("deprecation")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MD5PasswordEncoder();
    }

    private static class MD5PasswordEncoder implements PasswordEncoder {

        @Override
        public String encode(CharSequence rawPassword) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] messageDigest = md.digest(rawPassword.toString().getBytes());
                StringBuilder hexString = new StringBuilder();
                for (byte b : messageDigest) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }
                return hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("MD5 algorithm not found", e);
            }
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return encode(rawPassword).equals(encodedPassword);
        }
    }
}
