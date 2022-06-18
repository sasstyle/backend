package com.sasstyle.userservice.entity;

import com.sasstyle.userservice.controller.dto.JoinRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.UUID;

import static com.sasstyle.userservice.util.PasswordUtils.encode;
import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    private String name;

    @Enumerated(value = STRING)
    private Gender gender;

    @Column(nullable = false, unique = true)
    private String email;
    private String phoneNumber;

    @Embedded
    private Address address;

    //== 비지니스 메서드 ==//
    public static User create(JoinRequest request) {
        return User.builder()
                .userId(UUID.randomUUID().toString())
                .username(request.getUsername())
                .password(encode(request.getPassword()))
                .name(request.getName())
                .gender(request.getGender())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(new Address(request.getAddress()))
                .build();
    }
}
