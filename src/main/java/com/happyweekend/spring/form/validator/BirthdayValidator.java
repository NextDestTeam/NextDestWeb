package com.happyweekend.spring.form.validator;


import com.happyweekend.spring.form.validator.annotation.Birthday;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;


public class BirthdayValidator implements ConstraintValidator<Birthday,LocalDate> {


    public void initialize(Birthday birthday) {

    }


    @Override
    public boolean isValid(LocalDate birthday, ConstraintValidatorContext constraintValidatorContext) {

        boolean isValid = true;

        if(birthday ==null) return !isValid;

        if(birthday.isAfter(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())){
            return !isValid;
        }

        if(birthday.isBefore(
                LocalDate.from(new Date().toInstant().atZone(ZoneId.systemDefault()).minus(150,ChronoUnit.YEARS)))){
            return !isValid;
        }



        return isValid;

    }
}

