package com.happyweekend.spring.form;


import com.happyweekend.spring.App;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.boot.test.context.SpringBootTest;*/
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
/*@SpringBootTest(classes = App.class)*/
public class RegisterFormTest {

    private Validator validator;

    @Autowired
    private MessageSource messageSource;

    @Before
    public void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void testEqualPasswordValidation(){

        RegisterForm form = new RegisterForm();
        fillForm(form);
        form.setPassword("12345678");
        form.setRePassword("123456789");

        Set<ConstraintViolation<RegisterForm>> violations = validator.validate(form);

        assertEquals(1, violations.size());

        ConstraintViolation<RegisterForm> violation = violations.iterator().next();
        assertNotNull(messageSource);
        assertEquals(messageSource.getMessage("error.password.mismatch",null, Locale.ENGLISH),violation.getMessage());
    }

    @Test
    public void testEmailValidation(){
        RegisterForm form = new RegisterForm();
        fillForm(form);
        form.setEmail("a");

        Set<ConstraintViolation<RegisterForm>> violations = validator.validate(form);

        assertEquals(1, violations.size());
    }

    @Test
    public void testEmptyFirstName(){
        RegisterForm form = new RegisterForm();
        fillForm(form);
        form.setFirstName("");

        Set<ConstraintViolation<RegisterForm>> violations = validator.validate(form);

        assertEquals(1, violations.size());
    }

    @Test
    public void testValidForm(){
        RegisterForm form = new RegisterForm();
        fillForm(form);

        Set<ConstraintViolation<RegisterForm>> violations = validator.validate(form);

        assertEquals(0, violations.size());
    }

    @Test
    public void testNullFirstName(){
        RegisterForm form = new RegisterForm();
        fillForm(form);
        form.setFirstName(null);

        Set<ConstraintViolation<RegisterForm>> violations = validator.validate(form);

        assertEquals(1, violations.size());
    }

    private void fillForm(RegisterForm form) {
        form.setRePassword("12345678");
        form.setPassword("12345678");
        form.setEmail("lucas@gmail.com");
        form.setFirstName("Lucas");
        form.setLastName("Soares");
        form.setUsername("lucasale1");
    }


}