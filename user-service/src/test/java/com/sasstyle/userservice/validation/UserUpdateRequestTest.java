package com.sasstyle.userservice.validation;

import com.sasstyle.userservice.controller.dto.JoinRequest;
import com.sasstyle.userservice.entity.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JoinRequestTest {

    private ValidatorFactory factory;
    private Validator validator;

    private JoinRequest joinRequest;

    @BeforeEach
    void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        joinRequest = new JoinRequest(
                "sasstyle",
                "test1234!",
                "이순신",
                Gender.MAN,
                "lee@example.com",
                "010-1234-5678",
                "서울시 어딘가..."
        );
    }

    @Test
    @DisplayName("회원가입 성공 - 사용자 아이디가 4자리인 경우")
    void 회원가입_성공_아이디_4자() {
        joinRequest.setUsername("love");

        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("회원가입 실패 - 사용자 아이디가 3자리 이하인 경우")
    void 회원가입_실패_아이디_3자_이하() {
        joinRequest.setUsername("cat");

        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("회원가입 성공 - 사용자 아이디가 13자리인 경우")
    void 회원가입_성공_아이디_13자() {
        joinRequest.setUsername("sasstyle12345");

        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("회원가입 실패 - 사용자 아이디가 14자리 이상인 경우")
    void 회원가입_실패_아이디_14자_이상() {
        joinRequest.setUsername("sasstyle123456");

        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("회원가입 성공 - 사용자 비밀번호가 8자리인 경우")
    void 회원가입_성공_비밀번호_8자() {
        joinRequest.setPassword("lovelove");

        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("회원가입 실패 - 사용자 비밀번호가 7자리 이하인 경우")
    void 회원가입_실패_비밀번호_7자리_이하() {
        joinRequest.setPassword("yellow");

        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("회원가입 성공 - 사용자 비밀번호가 13자리인 경우")
    void 회원가입_성공_비밀번호_13자() {
        joinRequest.setPassword("sasstyle12345");

        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("회원가입 실패 - 사용자 비밀번호가 14자리 이상인 경우")
    void 회원가입_실패_비밀번호_14자_이상() {
        joinRequest.setPassword("sasstyle123456");

        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("회원가입 성공 - 사용자 이름이 2자리인 경우")
    void 회원가입_성공_이름_2자() {
        joinRequest.setName("홍길");

        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("회원가입 실패 - 사용자 이름이 1자리 이하인 경우")
    void 회원가입_실패_이름_1자리_이하() {
        joinRequest.setName("홍");

        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("회원가입 성공 - 사용자 이름이 6자리인 경우")
    void 회원가입_성공_이름_6자() {
        joinRequest.setName("홍길동홍길동");

        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("회원가입 실패 - 사용자 이름이 7자리 이상인 경우")
    void 회원가입_실패_이름_7자_이상() {
        joinRequest.setName("홍길동홍길동홍");

        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("회원가입 성공 - 이메일 형식이 옳바른 경우")
    void 이메일_형식_옳바름() {
        joinRequest.setEmail("sasstyle@example.com");

        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("회원가입 성공 - 이메일 형식이 유효하지 않은 경우(앳 없음)")
    void 이메일_형식_앳_없음() {
        joinRequest.setEmail("sasstyle");

        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("회원가입 성공 - 이메일 형식이 유효하지 않은 경우(앳만 있음)")
    void 이메일_형식_앳만_있음() {
        joinRequest.setEmail("sasstyle@");

        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("회원가입 성공 - 전화번호 형식이 옳바른 경우")
    void 전화번호_형식_옳바름() {
        joinRequest.setPhoneNumber("010-1234-5678");

        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("회원가입 성공 - 전화번호 형식이 옳바르지 않은 경우")
    void 전화번호_형식_옳바르지_않음() {
        joinRequest.setPhoneNumber("0101234-5678");
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);
        assertFalse(violations.isEmpty());

        joinRequest.setPhoneNumber("010-12345678");
        violations = validator.validate(joinRequest);
        assertFalse(violations.isEmpty());

        joinRequest.setPhoneNumber("01012345678");
        violations = validator.validate(joinRequest);
        assertFalse(violations.isEmpty());
    }
}
