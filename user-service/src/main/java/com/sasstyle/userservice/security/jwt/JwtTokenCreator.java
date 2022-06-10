package com.sasstyle.userservice.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sasstyle.userservice.controller.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
public final class JwtTokenCreator {

    private final Environment env;

    private static final String ENV_ISSUER = "token.issuer";
    private static final String ENV_SECRET = "token.secret";
    private static final String ENV_EXPIRATION_TIME = "token.expiration_time";

    private static final String ID_FIELD = "userId";
    private static final String USERNAME_FIELD = "username";

    public TokenResponse create(Long id, String username) {
        return new TokenResponse(createAccessToken(id, username), createRefreshToken());
    }

    private String createAccessToken(Long id, String username) {
        return JWT.create()
                .withIssuer(env.getProperty(ENV_ISSUER))
                .withClaim(ID_FIELD, id)
                .withClaim(USERNAME_FIELD, username)
                .withExpiresAt(expiresAt())
                .sign(Algorithm.HMAC512(env.getProperty(ENV_SECRET)));
    }

    private String createRefreshToken() {
        return null;
    }

    private Date expiresAt() {
        return new Date(
                new Date().getTime() + Long.parseLong(env.getProperty(ENV_EXPIRATION_TIME))
        );
    }
}
