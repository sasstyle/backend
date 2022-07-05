package com.sasstyle.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.sasstyle.userservice.util.PasswordUtils.encode;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    private String name;

    @Enumerated(value = STRING)
    private Role role;

    @Enumerated(value = STRING)
    private Gender gender;

    @Column(nullable = false, unique = true)
    private String email;
    private String phoneNumber;

    @Embedded
    private Address address;

    @Builder
    public User(Long id, String profileUrl, String userId, String username, String password, String name, Role role,
                Gender gender, String email, String phoneNumber, String address) {
        this.id = id;
        this.userProfile = UserProfile.builder()
                .profileUrl(profileUrl)
                .build();
        this.userId = userId;
        this.username = username;
        this.password = encode(password);
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = new Address(address);
    }

    //== 비지니스 메서드 ==//
    public void updateInfo(String name, Gender gender, String email, String phoneNumber, String address) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = new Address(address);
    }

    public void updateProfileUrl(String profileUrl) {
        this.userProfile = UserProfile.builder()
                .profileUrl(profileUrl)
                .build();
    }

    public void updatePassword(String password) {
        this.password = encode(password);
    }
}
