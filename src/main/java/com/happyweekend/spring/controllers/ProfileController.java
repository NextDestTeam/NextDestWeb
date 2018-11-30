package com.happyweekend.spring.controllers;

import com.happyweekend.adapter.PersonAdapter;
import com.happyweekend.models.Login;
import com.happyweekend.models.Person;
import com.happyweekend.service.LoginService;
import com.happyweekend.service.PersonService;
import com.happyweekend.service.PersonTypeService;
import com.happyweekend.spring.form.PersonForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class ProfileController {

    PersonService service = new PersonService();
    PersonTypeService personTypeService = new PersonTypeService();
    LoginService loginService = new LoginService();

    @GetMapping(path = "/profile")
    public ModelAndView index(HttpSession session){

        PersonAdapter adapter = new PersonAdapter();
        PersonForm personForm;

        Login login = (Login) session.getAttribute(LoginController.USER_LOGIN_SESSION);
        Person person = service.get(login.getPersonId());

        personForm = adapter.toPersonForm(person,login,personTypeService.getAll());
        personForm.setPassword("");

        ModelAndView modelAndView = new ModelAndView("profile.html",
                "personForm",
                personForm);

        return  modelAndView;
    }

    @PostMapping(path = "/profile")
    public ModelAndView save(@Valid PersonForm personForm, BindingResult result){

        Person personDatabase = service.get(personForm.getId());

        boolean passwordError = false;
        boolean passwordUpdated = false;
        if( !personForm.getPassword().equals("") && personForm.getPassword()!=null){

            if(!personForm.getPassword().equals(personForm.getRePassword())) {
                passwordError = true;
            }else {
                passwordUpdated = true;
            }


        }

        if(passwordError){
            result.addError(new FieldError(result.getObjectName(),"password","error.password.mismatch"));
            result.addError(new FieldError(result.getObjectName(),"rePassword","error.password.mismatch"));
            personForm.setPassword("");
            personForm.setRePassword("");
        }

        if(!(result.getErrorCount()==4 && !passwordUpdated) && result.hasErrors()){
            return new ModelAndView("profile.html",
                    "personForm",
                    personForm);
        }

        PersonAdapter adapter = new PersonAdapter();

        Person person = adapter.toPerson(personForm);

        person.setId(personDatabase.getId());
        person.getLogin().setLoginName(personDatabase.getLogin().getLoginName());
        person.setPersonTypeId(personDatabase.getPersonTypeId());

        if(passwordUpdated) service.save(person,loginService);
        else service.save(person);


        return new ModelAndView("redirect:/");

    }

}
