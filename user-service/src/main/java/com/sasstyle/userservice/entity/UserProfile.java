package com.sasstyle.userservice.entity;

import lombok.*;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class UserProfile extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "user_profile_id")
    private Long id;

    private String profileUrl;
}
