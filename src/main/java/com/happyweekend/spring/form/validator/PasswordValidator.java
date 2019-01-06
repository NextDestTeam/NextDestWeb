package com.happyweekend.spring.form.validator;

import com.happyweekend.spring.form.RegisterForm;
import com.happyweekend.spring.form.validator.annotation.EqualPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;


public class PasswordValidator implements ConstraintValidator<EqualPassword,Object> {

    private String pass1;
    private String pass2;

    public void initialize(EqualPassword password) {
        this.pass1 = password.pass1();
        this.pass2 = password.pass2();
    }


    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        //boolean isValid = registerForm.getPassword().equals(registerForm.getRePassword());
        boolean isValid = false;
        try {

            Class<?> clazz = object.getClass();
            Field f1 = clazz.getDeclaredField(pass1);
            Field f2 = clazz.getDeclaredField(pass2);
            f1.setAccessible(true);
            f2.setAccessible(true);
            Object o1 = f1.get(object);
            Object o2 = f2.get(object);

            isValid = o1.equals(o2);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return isValid;

    }
}

