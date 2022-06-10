package com.sasstyle.userservice.service;

import com.sasstyle.userservice.controller.dto.JoinRequest;
import com.sasstyle.userservice.controller.dto.JoinResponse;
import com.sasstyle.userservice.controller.dto.LoginRequest;
import com.sasstyle.userservice.entity.User;
import com.sasstyle.userservice.repository.UserRepository;
import com.sasstyle.userservice.security.auth.PrincipalDetails;
import com.sasstyle.userservice.security.jwt.JwtTokenCreator;
import com.sasstyle.userservice.security.jwt.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenCreator jwtTokenCreator;

    public TokenResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(
                request.getUsername(),
                request.getPassword()
        );

        Authentication authenticate = authenticationManager.authenticate(unauthenticated);
        PrincipalDetails principal = (PrincipalDetails) authenticate.getPrincipal();

        return jwtTokenCreator.create(
                principal.getUser().getId(),
                principal.getUsername()
        );
    }

    @Transactional
    public JoinResponse create(JoinRequest request) {
        User savedUser = userRepository.save(User.create(request));

        return new JoinResponse(savedUser.getId(), savedUser.getUsername());
    }
}
