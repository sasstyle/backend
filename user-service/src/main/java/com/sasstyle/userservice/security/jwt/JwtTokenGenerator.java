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
public class JwtTokenGenerator {

    private final Environment env;

    private static final String ISSUER_ENV_NAME = "token.issuer";
    private static final String SECRET_ENV_NAME = "token.secret";
    private static final String EXPIRATION_TIME_ENV_NAME = "token.expiration_time";

    private static final String USER_ID_FIELD = "userId";
    private static final String USERNAME_FIELD = "username";

    public TokenResponse createToken(String userId, String username) {
        return new TokenResponse(createAccessToken(userId, username), createRefreshToken());
    }

    public String createAccessToken(String userId, String username) {
        return JWT.create()
                .withIssuer(getProperty(ISSUER_ENV_NAME))
                .withClaim(USER_ID_FIELD, userId)
                .withClaim(USERNAME_FIELD, username)
                .withExpiresAt(expiresAt())
                .sign(Algorithm.HMAC512(getProperty(SECRET_ENV_NAME)));
    }

    private String createRefreshToken() {
        return null;
    }

    private Date expiresAt() {
        return new Date(
                new Date().getTime() + Long.parseLong(getProperty(EXPIRATION_TIME_ENV_NAME))
        );
    }

    private String getProperty(String expirationTimeEnvName) {
        return env.getProperty(expirationTimeEnvName);
    }
}
