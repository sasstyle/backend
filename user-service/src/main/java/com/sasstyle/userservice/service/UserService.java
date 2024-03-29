package com.sasstyle.userservice.service;

import com.sasstyle.userservice.client.CartServiceClient;
import com.sasstyle.userservice.controller.dto.*;
import com.sasstyle.userservice.entity.Role;
import com.sasstyle.userservice.entity.User;
import com.sasstyle.userservice.entity.UserProfile;
import com.sasstyle.userservice.error.exception.DuplicatedException;
import com.sasstyle.userservice.error.exception.DuplicatedUsernameException;
import com.sasstyle.userservice.error.exception.UserNotFoundException;
import com.sasstyle.userservice.repository.UserRepository;
import com.sasstyle.userservice.security.auth.PrincipalDetails;
import com.sasstyle.userservice.security.jwt.JwtTokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final CartServiceClient cartServiceClient;

    public User findByUserId(String userId) {
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new UserNotFoundException("유저를 찾을 수 없습니다.");
        }

        return user;
    }

    public TokenResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(
                request.getUsername(),
                request.getPassword()
        );

        Authentication authenticate = authenticationManager.authenticate(unauthenticated);
        PrincipalDetails principal = (PrincipalDetails) authenticate.getPrincipal();

        return jwtTokenGenerator.createToken(
                principal.getUser().getUserId(),
                principal.getUsername()
        );
    }

    @Transactional
    public JoinResponse createUser(JoinRequest request) {
        if (isDuplicateUsername(request.getUsername())) {
            throw new DuplicatedUsernameException("회원의 아이디가 이미 등록됐습니다.");
        }

        if (isDuplicateEmail(request.getEmail())) {
            throw new DuplicatedException("회원의 이메일이 이미 등록됐습니다.");
        }

        // 유저 프로필 엔티티 생성
        UserProfile userProfile = UserProfile.builder()
                .profileUrl(request.getProfileUrl())
                .build();

        // 유저 생성
        User user = User.builder()
                .userProfile(userProfile)
                .userId(UUID.randomUUID().toString())
                .username(request.getUsername())
                .password(request.getPassword())
                .name(request.getName())
                .gender(request.getGender())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .role(Role.valueOf(request.getRole().name()))
                .build();

        User savedUser = userRepository.save(user);

        cartServiceClient.createCart(savedUser.getUserId());

        return new JoinResponse(savedUser.getUserId(), savedUser.getUsername());
    }

    @Transactional
    public User updateUser(String userId, UserUpdateRequest request) {
        User user = findByUserId(userId);

        if (hasProfileUrl(request.getProfileUrl())) {
            UserProfile userProfile = user.getUserProfile();
            userProfile.setProfileUrl(request.getProfileUrl());
        }

        if (hasPassword(request.getPassword())) {
            user.updatePassword(request.getPassword());
        }

        user.updateInfo(request.getName(),
                request.getGender(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getAddress());

        return user;
    }

    @Transactional
    public void deleteUser(String userId) {
        User user = userRepository.findByUserId(userId);

        userRepository.delete(user);
    }

    private boolean hasProfileUrl(String profileUrl) {
        return StringUtils.hasText(profileUrl);
    }

    public boolean hasPassword(String password) {
        return StringUtils.hasText(password);
    }

    private boolean isDuplicateUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean isDuplicateEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
