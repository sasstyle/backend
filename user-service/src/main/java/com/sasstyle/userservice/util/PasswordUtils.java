package com.sasstyle.userservice.util;

import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class PasswordUtils {

    public static PasswordEncoder getEncoder() {
        return LazyHolder.ENCODER;
    }

    public static String encode(String rawPassword) {
        return getEncoder().encode(rawPassword);
    }

    private static class LazyHolder {
        private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    }
}
