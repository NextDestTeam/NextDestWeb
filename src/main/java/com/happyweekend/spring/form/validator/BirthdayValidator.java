package com.happyweekend.spring.form.validator;


import com.happyweekend.spring.form.validator.annotation.Birthday;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.temporal.ChronoUnit;
import java.util.Date;


public class BirthdayValidator implements ConstraintValidator<Birthday,Date> {


    public void initialize(Birthday birthday) {

    }


    @Override
    public boolean isValid(Date birthday, ConstraintValidatorContext constraintValidatorContext) {

        boolean isValid = true;

        if(birthday ==null) return !isValid;

        if(birthday.after(new Date())){
            return !isValid;
        }

        if(birthday.before(Date.from(new Date().toInstant().minus(150,ChronoUnit.YEARS)))){
            return !isValid;
        }



        return isValid;

    }
}

