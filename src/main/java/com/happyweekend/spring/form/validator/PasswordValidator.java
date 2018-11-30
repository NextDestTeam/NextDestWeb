package com.happyweekend.spring.form.validator;

import com.happyweekend.spring.form.RegisterForm;
import com.happyweekend.spring.form.validator.annotation.EqualPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


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
            isValid = RegisterForm.class.getField(pass1).get(object)
                    .equals(RegisterForm.class.getField(pass2).get(object));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return isValid;

    }
}
