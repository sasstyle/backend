package com.sasstyle.userservice.service;

import com.sasstyle.userservice.UserDummy;
import com.sasstyle.userservice.controller.dto.*;
import com.sasstyle.userservice.entity.Gender;
import com.sasstyle.userservice.entity.Role;
import com.sasstyle.userservice.entity.User;
import com.sasstyle.userservice.error.exception.DuplicatedException;
import com.sasstyle.userservice.error.exception.DuplicatedUsernameException;
import com.sasstyle.userservice.error.exception.UserNotFoundException;
import com.sasstyle.userservice.repository.UserRepository;
import com.sasstyle.userservice.security.auth.PrincipalDetails;
import com.sasstyle.userservice.security.jwt.JwtTokenGenerator;
import com.sasstyle.userservice.util.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.authentication.UsernamePasswordAuthenticationToken.authenticated;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    private JwtTokenGenerator jwtTokenGenerator;

    @Mock
    private Environment env;

    private PasswordEncoder passwordEncoder = PasswordUtils.getEncoder();

    private User user;

    private JoinRequest joinRequest;

    @BeforeEach
    void setUp() {
        jwtTokenGenerator = new JwtTokenGenerator(env);
        userService = new UserService(userRepository, authenticationManager, jwtTokenGenerator);

        user = UserDummy.user();
        joinRequest = UserDummy.join();
    }

    @Test
    @DisplayName("로그인 성공")
    void 로그인() {
        Authentication authenticate = authenticated(new PrincipalDetails(user), user.getPassword(), null);
        given(authenticationManager.authenticate(any())).willReturn(authenticate);
        given(env.getProperty("token.issuer")).willReturn("tester");
        given(env.getProperty("token.secret")).willReturn("test");
        given(env.getProperty("token.expiration_time")).willReturn("3600");


        TokenResponse tokenResponse = userService.login(new LoginRequest(user.getUsername(), user.getPassword()));

        assertThat(tokenResponse).isNotNull();
        assertThat(tokenResponse.getAccessToken()).isNotNull();
    }

    @Test
    @DisplayName("회원가입 성공")
    void 회원가입() {
        given(userRepository.existsByUsername(user.getUsername())).willReturn(false);
        given(userRepository.existsByEmail(user.getEmail())).willReturn(false);
        given(userRepository.save(any())).willReturn(user);

        JoinResponse response = userService.createUser(joinRequest);

        assertThat(user.getUserId()).isEqualTo(response.getUserId());
        assertThat(user.getUsername()).isEqualTo(response.getUsername());
        assertThat(user.getRole()).isEqualTo(Role.USER);
    }

    @Test
    @DisplayName("회원가입 실패 - 사용자 아이디가 중복된 경우")
    void 회원가입_아이디_중복() {
        given(userRepository.existsByUsername(user.getUsername())).willReturn(true);

        assertThrows(DuplicatedUsernameException.class, () -> {
            userService.createUser(joinRequest);
        });
    }

    @Test
    @DisplayName("회원가입 실패 - 사용자 아이디가 중복된 경우")
    void 회원가입_이메일_중복() {
        given(userRepository.existsByUsername(user.getUsername())).willReturn(false);
        given(userRepository.existsByEmail(user.getEmail())).willReturn(true);

        assertThrows(DuplicatedException.class, () -> {
            userService.createUser(joinRequest);
        });
    }

    @Test
    @DisplayName("내 정보 조회 성공")
    void 내_정보_조회() {
        String userId = user.getUserId(); // 02a5c76e-fa26-4ea6-a797-0756a09e7f76

        given(userRepository.findByUserId(userId)).willReturn(user);

        User findUser = userService.findByUserId(this.user.getUserId());

        assertThat(user.getUserId()).isEqualTo(findUser.getUserId());
        assertThat(user.getUsername()).isEqualTo(findUser.getUsername());
        assertThat(user.getName()).isEqualTo(findUser.getName());
        assertThat(user.getEmail()).isEqualTo(findUser.getEmail());
        assertThat(user.getGender()).isEqualTo(findUser.getGender());
        assertThat(user.getPhoneNumber()).isEqualTo(findUser.getPhoneNumber());
        assertThat(user.getAddress()).isEqualTo(findUser.getAddress());
        assertThat(user.getRole()).isEqualTo(findUser.getRole());
    }


    @Test
    @DisplayName("내 정보 조회 실패 - userId가 잘못된 경우")
    void 내_정보_조회_실패_UserId_잘못() {
        String userId = user.getUserId() + "Dummy";

        given(userRepository.findByUserId(userId)).willReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            userService.findByUserId(userId);
        });
    }

    @Test
    @DisplayName("회원정보 수정 성공 - 모든 정보를 수정하는 경우")
    void 회원정보_수정_성공() {
        String userId = user.getUserId();


        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(
                "https://picsum.photos/seed/picsum/500/500",
                "test1234",
                "홍길동",
                Gender.MAN,
                "test1234@test.com",
                "010-9876-5432",
                "제주도 어딘가..."
        );

        given(userRepository.findByUserId(userId)).willReturn(user);

        User updateUser = userService.updateUser(userId, userUpdateRequest);

        assertTrue(passwordEncoder.matches(userUpdateRequest.getPassword(), updateUser.getPassword()));
        assertThat(updateUser.getProfileUrl()).isEqualTo(userUpdateRequest.getProfileUrl());
        assertThat(updateUser.getName()).isEqualTo(userUpdateRequest.getName());
        assertThat(updateUser.getGender()).isEqualTo(userUpdateRequest.getGender());
        assertThat(updateUser.getEmail()).isEqualTo(userUpdateRequest.getEmail());
        assertThat(updateUser.getPhoneNumber()).isEqualTo(userUpdateRequest.getPhoneNumber());
        assertThat(updateUser.getAddress().getDetails()).isEqualTo(userUpdateRequest.getAddress());
    }

    @Test
    @DisplayName("회원정보 수정 성공 - 프로필, 이름, 성별, 이메일, 전화번호, 주소를 수정하는 경우")
    void 회원정보_수정_성공_프로필() {
        String userId = user.getUserId();


        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(
                "https://picsum.photos/seed/picsum/500/500",
                null,
                "홍길동",
                Gender.MAN,
                "test1234@test.com",
                "010-9876-5432",
                "제주도 어딘가..."
        );

        given(userRepository.findByUserId(userId)).willReturn(user);

        User updateUser = userService.updateUser(userId, userUpdateRequest);

        assertThat(updateUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(updateUser.getProfileUrl()).isEqualTo(userUpdateRequest.getProfileUrl());
        assertThat(updateUser.getName()).isEqualTo(userUpdateRequest.getName());
        assertThat(updateUser.getGender()).isEqualTo(userUpdateRequest.getGender());
        assertThat(updateUser.getEmail()).isEqualTo(userUpdateRequest.getEmail());
        assertThat(updateUser.getPhoneNumber()).isEqualTo(userUpdateRequest.getPhoneNumber());
        assertThat(updateUser.getAddress().getDetails()).isEqualTo(userUpdateRequest.getAddress());
    }

    @Test
    @DisplayName("회원정보 수정 성공 - 비밀번호, 이름, 성별, 이메일, 전화번호, 주소를 수정하는 경우")
    void 회원정보_수정_성공_비밀번호() {
        String userId = user.getUserId();

        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(
                null,
                "test1234",
                "홍길동",
                Gender.MAN,
                "test1234@test.com",
                "010-9876-5432",
                "제주도 어딘가..."
        );

        given(userRepository.findByUserId(userId)).willReturn(user);

        User updateUser = userService.updateUser(userId, userUpdateRequest);

        assertThat(updateUser.getProfileUrl()).isEqualTo(user.getProfileUrl());
        assertTrue(passwordEncoder.matches(userUpdateRequest.getPassword(), updateUser.getPassword()));
        assertThat(updateUser.getName()).isEqualTo(userUpdateRequest.getName());
        assertThat(updateUser.getGender()).isEqualTo(userUpdateRequest.getGender());
        assertThat(updateUser.getEmail()).isEqualTo(userUpdateRequest.getEmail());
        assertThat(updateUser.getPhoneNumber()).isEqualTo(userUpdateRequest.getPhoneNumber());
        assertThat(updateUser.getAddress().getDetails()).isEqualTo(userUpdateRequest.getAddress());
    }

    @Test
    @DisplayName("회원정보 수정 실패 - 존재하지 않는 계정을 수정하는 경우")
    void 회원정보_수정_실패_존재X() {
        String userId = user.getUserId();

        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(
                user.getProfileUrl(),
                "test1234",
                "홍길동",
                Gender.MAN,
                "test1234@test.com",
                "010-9876-5432",
                "제주도 어딘가..."
        );

        given(userRepository.findByUserId(userId)).willReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(userId, userUpdateRequest);
        });
    }

    @Test
    @DisplayName("회원탈퇴 성공")
    void 회원탈퇴_성공() {
        String userId = user.getUserId();

        given(userRepository.findByUserId(userId)).willReturn(null);
        userService.deleteUser(userId);

        assertThrows(UserNotFoundException.class, () -> {
            userService.findByUserId(userId);
        });
    }

}