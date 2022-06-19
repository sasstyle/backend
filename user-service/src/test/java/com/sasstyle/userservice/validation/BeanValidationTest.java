package com.sasstyle.userservice.validation;

import org.junit.jupiter.api.BeforeEach;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class BeanValidation {

    protected ValidatorFactory factory;
    protected Validator validator;

    @BeforeEach
    void setUp() {

    }
}
