package com.happyweekend.spring.form.validator.annotation;

import com.happyweekend.spring.form.validator.BirthdayValidator;
import com.happyweekend.spring.form.validator.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {BirthdayValidator.class})
public @interface Birthday {

    String message() default "Invalid Birthday";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

