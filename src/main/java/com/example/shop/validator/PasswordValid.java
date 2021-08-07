package com.example.shop.validator;

import com.example.shop.validator.impl.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PasswordValid {
    String message() default "confirmed password is different than password";

    Class<?>[] groups() default {
    };

    Class<? extends Payload>[] payload() default {};
}