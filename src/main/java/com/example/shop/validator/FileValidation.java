package com.example.shop.validator;

import com.example.shop.validator.impl.FileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = FileValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FileValidation {
    String message() default "file is different type than jpg or png";

    Class<?>[] groups() default {
    };

    Class<? extends Payload>[] payload() default {};
}
