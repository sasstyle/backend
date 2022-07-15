package com.sasstyle.orderservice.entity;

import lombok.*;

import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class Address {

    private String details;
}
