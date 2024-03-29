package com.sasstyle.userservice.validation;

import com.sasstyle.userservice.UserDummy;
import com.sasstyle.userservice.controller.dto.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserUpdateRequestTest extends BeanValidationTest {

    private UserUpdateRequest request;

    @BeforeEach
    void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        request = UserDummy.updateRequest();
    }

    @Test
    @DisplayName("회원수정 성공 - 프로필 이미지를 수정한 경우")
    void 회원수정_성공_프로필_이미지_수정() {
        request.setProfileUrl("https://picsum.photos/seed/picsum/500/500");

        isUpdateTrue();
    }

    @Test
    @DisplayName("회원수정 성공 - 프로필 이미지를 입력하지 않는 경우")
    void 회원수정_성공_프로필_이미지_입력_X() {
        request.setProfileUrl("");

        isUpdateTrue();
    }

    @Test
    @DisplayName("회원수정 성공 - 사용자 비밀번호가 8자리인 경우(숫자 특수문자 포함)")
    void 회원수정_성공_비밀번호_8자_숫특() {
        request.setPassword("lovelo1!");

        isUpdateTrue();
    }

    @Test
    @DisplayName("회원수정 실패 - 사용자 비밀번호가 8자리인 경우(숫자 특수문자 X)")
    void 회원수정_실패_비밀번호_8자() {
        request.setPassword("lovelove");

        isUpdateFalse();
    }

    @Test
    @DisplayName("회원수정 실패 - 사용자 비밀번호가 7자리 이하인 경우")
    void 회원수정_실패_비밀번호_7자리_이하() {
        request.setPassword("yellow");

        isUpdateFalse();
    }

    @Test
    @DisplayName("회원수정 성공 - 사용자 비밀번호가 13자리인 경우")
    void 회원수정_성공_비밀번호_13자() {
        request.setPassword("sasstyle1234!");

        isUpdateTrue();
    }

    @Test
    @DisplayName("회원수정 실패 - 사용자 비밀번호가 14자리 이상인 경우")
    void 회원수정_실패_비밀번호_14자_이상() {
        request.setPassword("sasstyle123456");

        isUpdateFalse();
    }

    @Test
    @DisplayName("회원수정 실패 - 사용자 이름을 입력하지 않는 경우")
    void 회원수정_실패_이름_입력_X() {
        request.setName(null);

        isUpdateFalse();
    }

    @Test
    @DisplayName("회원수정 성공 - 사용자 이름이 2자리인 경우")
    void 회원수정_성공_이름_2자() {
        request.setName("홍길");

        isUpdateTrue();
    }

    @Test
    @DisplayName("회원수정 실패 - 사용자 이름이 1자리 이하인 경우")
    void 회원수정_실패_이름_1자리_이하() {
        request.setName("홍");

        isUpdateFalse();
    }

    @Test
    @DisplayName("회원수정 성공 - 사용자 이름이 6자리인 경우")
    void 회원수정_성공_이름_6자() {
        request.setName("홍길동홍길동");

        isUpdateTrue();
    }

    @Test
    @DisplayName("회원수정 실패 - 사용자 이름이 11자리 이상인 경우")
    void 회원수정_실패_이름_11자_이상() {
        request.setName("홍길동홍길동홍길동홍길동");

        isUpdateFalse();
    }

    @Test
    @DisplayName("회원수정 실패 - 사용자 이메일을 입력하지 않는 경우")
    void 회원수정_실패_이메일_입력_X() {
        request.setEmail(null);

        isUpdateFalse();
    }

    @Test
    @DisplayName("회원수정 실패 - 사용자 이름에 특수문자가 포함된 경우")
    void 회원수정_실패_이름_특문() {
        request.setName("이순신!");

        isUpdateFalse();
    }

    @Test
    @DisplayName("회원수정 성공 - 이메일 형식이 옳바른 경우")
    void 회원수정_성공_이메일_형식_옳바름() {
        request.setEmail("sasstyle@example.com");

        isUpdateTrue();
    }

    @Test
    @DisplayName("회원수정 실패 - 이메일 형식이 유효하지 않은 경우(앳 없음)")
    void 회원수정_실패_이메일_형식_앳_없음() {
        request.setEmail("sasstyle");

        isUpdateFalse();
    }

    @Test
    @DisplayName("회원수정 실패 - 이메일 형식이 유효하지 않은 경우(앳만 있음)")
    void 회원수정_실패_이메일_형식_앳만_있음() {
        request.setEmail("sasstyle@");

        isUpdateFalse();
    }

    @Test
    @DisplayName("회원수정 실패 - 사용자 전화번호를 입력하지 않는 경우")
    void 회원수정_실패_전화번호_입력_X() {
        request.setPhoneNumber(null);

        isUpdateFalse();
    }

    @Test
    @DisplayName("회원수정 성공 - 전화번호 형식이 옳바른 경우")
    void 회원수정_성공_전화번호_형식_옳바름() {
        request.setPhoneNumber("010-1234-5678");

        isUpdateTrue();
    }

    @Test
    @DisplayName("회원수정 실패 - 전화번호 형식이 옳바르지 않은 경우")
    void 회원수정_실패_전화번호_형식_옳바르지_않음() {
        request.setPhoneNumber("0101234-5678");
        isUpdateFalse();

        request.setPhoneNumber("010-12345678");
        isUpdateFalse();

        request.setPhoneNumber("01012345678");
        isUpdateFalse();
    }

    private void isUpdateTrue() {
        Set<ConstraintViolation<UserUpdateRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    private void isUpdateFalse() {
        Set<ConstraintViolation<UserUpdateRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }
}
