package com.sasstyle.userservice.service;

import com.sasstyle.userservice.UserDummy;
import com.sasstyle.userservice.controller.dto.JoinRequest;
import com.sasstyle.userservice.controller.dto.JoinResponse;
import com.sasstyle.userservice.controller.dto.LoginRequest;
import com.sasstyle.userservice.controller.dto.TokenResponse;
import com.sasstyle.userservice.entity.Gender;
import com.sasstyle.userservice.entity.User;
import com.sasstyle.userservice.error.exception.DuplicatedException;
import com.sasstyle.userservice.error.exception.DuplicatedUsernameException;
import com.sasstyle.userservice.error.exception.UserNotFoundException;
import com.sasstyle.userservice.repository.UserRepository;
import com.sasstyle.userservice.security.auth.PrincipalDetails;
import com.sasstyle.userservice.security.jwt.JwtTokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private User user;

    private JoinRequest joinRequest;

    @BeforeEach
    void beforeEach() {
        jwtTokenGenerator = new JwtTokenGenerator(env);
        userService = new UserService(userRepository, authenticationManager, jwtTokenGenerator);

        user = UserDummy.user();
        joinRequest = new JoinRequest("sasstyle",
                "test1234!",
                "이순신",
                Gender.MAN,
                "lee@example.com",
                "010-1234-5678",
                "서울시 어딘가..."
        );
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

        JoinResponse response = userService.create(joinRequest);

        assertThat(user.getUserId()).isEqualTo(response.getUserId());
        assertThat(user.getUsername()).isEqualTo(response.getUsername());
    }

    @Test
    @DisplayName("회원가입 실패 - 사용자 아이디가 중복된 경우")
    void 회원가입_아이디_중복() {
        given(userRepository.existsByUsername(user.getUsername())).willReturn(true);

        assertThrows(DuplicatedUsernameException.class, () -> {
            userService.create(joinRequest);
        });
    }

    @Test
    @DisplayName("회원가입 실패 - 사용자 아이디가 중복된 경우")
    void 회원가입_이메일_중복() {
        given(userRepository.existsByUsername(user.getUsername())).willReturn(false);
        given(userRepository.existsByEmail(user.getEmail())).willReturn(true);

        assertThrows(DuplicatedException.class, () -> {
            userService.create(joinRequest);
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
}