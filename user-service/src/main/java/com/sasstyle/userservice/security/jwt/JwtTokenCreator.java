package com.sasstyle.userservice.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sasstyle.userservice.controller.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenCreator {

    @Value("${token.issuer}")
    private final String issuer;

    @Value("${token.secret}")
    private final String secret;

    @Value("${token.expiration_time}")
    private final String expirationTime;

    private static final String ID_FIELD = "userId";
    private static final String USERNAME_FIELD = "username";

    public TokenResponse create(Long id, String username) {
        return new TokenResponse(createAccessToken(id, username), createRefreshToken());
    }

    public String createAccessToken(Long id, String username) {
        return JWT.create()
                .withIssuer(issuer)
                .withClaim(ID_FIELD, id)
                .withClaim(USERNAME_FIELD, username)
                .withExpiresAt(expiresAt())
                .sign(Algorithm.HMAC512(secret));
    }

    private String createRefreshToken() {
        return null;
    }

    private Date expiresAt() {
        return new Date(
                new Date().getTime() + Long.parseLong(expirationTime)
        );
    }
}
