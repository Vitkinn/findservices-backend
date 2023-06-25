package com.findservices.serviceprovider.login.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.findservices.serviceprovider.login.model.LoginEntity;
import com.findservices.serviceprovider.login.model.TokenDto;
import com.findservices.serviceprovider.user.model.UserEntity;
import com.findservices.serviceprovider.user.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; // 1h

    @Autowired
    private FirebaseService firebaseService;

    public TokenDto generateToken(LoginEntity login) {
        Instant expiresAt = Instant.now().plus(validityInMilliseconds, ChronoUnit.MILLIS);
        String token = JWT.create()
                .withIssuer("serviceProvider")
                .withSubject(login.getUsername())
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC256(secretKey));

        UserEntity user = login.getUser();
        return TokenDto.builder()
                .accessToken(token)
                .expiration(expiresAt)
                .role(login.getRole())
                .id(login.getId())
                .login(login.getUsername())
                .username(user.getName().concat(user.getLastName()))
                .photoUrl(user.getUserPhotoUrl() != null ? firebaseService.getImageUrl(user.getUserPhotoUrl()): null)
                .build();
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .withIssuer("serviceProvider")
                .build()
                .verify(token)
                .getSubject();
    }
}
