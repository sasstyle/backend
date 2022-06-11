package com.sasstyle.userservice.security.auth;

import com.sasstyle.userservice.UserDummy;
import com.sasstyle.userservice.entity.Address;
import com.sasstyle.userservice.entity.Gender;
import com.sasstyle.userservice.entity.User;
import com.sasstyle.userservice.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PrincipalDetailsServiceTest {

    @InjectMocks
    private PrincipalDetailsService detailsService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void beforeEach() {
        user = UserDummy.user();
    }

    @Test
    @DisplayName("로그인 성공 - 사용자 아이디가 있는 경우")
    void 아이디_O() {
        given(userRepository.findByUsername(user.getUsername())).willReturn(user);

        UserDetails details = detailsService.loadUserByUsername(user.getUsername());

        assertThat(details).isNotNull();
        assertThat(details.getUsername()).isEqualTo(user.getUsername());
        assertThat(details.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    @DisplayName("로그인 실패 - 사용자 아이디가 없는 경우")
    void 아이디_X() {
        given(userRepository.findByUsername(anyString())).willReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            detailsService.loadUserByUsername(user.getUsername());
        });
    }
}