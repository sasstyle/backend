package com.sasstyle.gatewayservice.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtValidator {

    private final Environment env;

    private static final String ENV_ISSUER = "token.issuer";
    private static final String ENV_SECRET = "token.secret";
    private static final String ENV_EXPIRATION_TIME = "token.expiration_time";

    public boolean isValid(String jwt) {
        try {
            DecodedJWT verify = getDecodedJWT(jwt);

            String userId = verify.getClaim("userId").asString();
            String username = verify.getClaim("username").asString();

            if (StringUtils.hasText(userId) && StringUtils.hasText(username)) {
                log.info("userId: {}, username: {}", userId, username);
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return false;
    }

    public String getUserId(String jwt) {
        try {
            DecodedJWT verify = getDecodedJWT(jwt);
            String userId = verify.getClaim("userId").asString();

            return userId;
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }

    private DecodedJWT getDecodedJWT(String jwt) {
        DecodedJWT verify = JWT
                .require(Algorithm.HMAC512(env.getProperty(ENV_SECRET)))
                .withIssuer(env.getProperty(ENV_ISSUER))
                .acceptExpiresAt(Long.parseLong(env.getProperty(ENV_EXPIRATION_TIME)))
                .build()
                .verify(jwt);

        return verify;
    }
}
