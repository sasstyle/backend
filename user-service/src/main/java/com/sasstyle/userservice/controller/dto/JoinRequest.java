package com.sasstyle.userservice.controller.dto;

import com.sasstyle.userservice.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Schema(description = "회원가입 요청 DTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {

    @Size(min = 4, max = 13, message = "아이디는 4~13자리로 입력해주세요.")
    @Schema(description = "아이디", example = "sasstyle", required = true)
    private String username;

    @Size(min = 8, max =  13, message = "비밀번호는 8~13자리로 입력해주세요.")
    @Schema(description = "비밀번호", example = "test1234!", required = true)
    private String password;

    @Size(min = 2, max = 6, message = "이름은 2~6자리로 입력해주세요.")
    @Schema(description = "이름", example = "이순신", required = true)
    private String name;

    @Schema(description = "성별", example = "남자", required = true)
    private Gender gender;

    @Email(message = "이메일이 형식이 유효하지 않습니다.")
    @Schema(description = "이메일", example = "lee@example.com", required = true)
    private String email;

    @Pattern(regexp = "(01[1|6|7|8|9|0])-(\\d{3,4})-(\\d{4})$", message = "휴대폰 형식이 유효하지 않습니다.")
    @Schema(description = "전화번호", example = "010-1234-5678", required = true)
    private String phoneNumber;

    // Address
    @Schema(description = "주소", example = "서울시 어딘가...", required = true)
    private String address;
}
