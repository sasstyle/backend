package com.sasstyle.userservice.controller.dto;

import com.sasstyle.userservice.entity.Address;
import com.sasstyle.userservice.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {

    private String username;
    private String password;
    private String name;
    private Gender gender;
    private String email;
    private String phoneNumber;

    // Address
    private String city;
    private String street;
    private String zipcode;
}
