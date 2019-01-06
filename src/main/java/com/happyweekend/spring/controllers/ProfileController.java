package com.happyweekend.spring.controllers;

import com.happyweekend.adapter.PersonAdapter;
import com.happyweekend.models.Image;
import com.happyweekend.models.Login;
import com.happyweekend.models.Person;
import com.happyweekend.service.ImageService;
import com.happyweekend.service.LoginService;
import com.happyweekend.service.PersonService;
import com.happyweekend.service.PersonTypeService;
import com.happyweekend.spring.form.PersonForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.MessageSourceResourceBundle;
import org.springframework.context.support.MessageSourceSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Locale;

@Controller

public class ProfileController {

    private static final String PROFILE_IMAGE_FILENAME = "profile_image_filename";
    private static final String PROFILE_IMAGE = "profile_image";
    @Autowired
    private MessageSource messageSource;

    PersonService service = new PersonService();
    PersonTypeService personTypeService = new PersonTypeService();
    LoginService loginService = new LoginService();
    ImageService imageService = new ImageService();

    @GetMapping(path = "/profile")
    public ModelAndView index(HttpSession session){

        PersonAdapter adapter = new PersonAdapter();
        PersonForm personForm;

        session.removeAttribute(PROFILE_IMAGE_FILENAME);
        session.removeAttribute(PROFILE_IMAGE);

        Login login = (Login) session.getAttribute(LoginController.USER_LOGIN_SESSION);
        Person person = service.get(login.getPersonId());

        ImageService imageService = new ImageService();

        personForm = adapter.toPersonForm(person,login,personTypeService.getAll(),imageService.get(person.getImageId()));
        personForm.setPassword(null);
        personForm.setRePassword(null);

        if(personForm.getPhoto()==null){
            personForm.setPhoto("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGcAAABnCAMAAAAqn6zLAAABKVBMVEVsa2tta2ttbGxubGxubW1vbW1vbm5wbm5wb29xcHBycHBycXFzcXFzcnJ0c3N1dHR2dXV3dnZ4d3d5eHh5eXl6eXl7enp8enp8e3t9fHx+fX1/fn6BgICEg4OFhISJiIiLioqMi4uNjY2Pjo6Qjo6Qj4+SkZGUk5OVlJSXlpabmpqcm5uenZ2fnp6gn5+goKCioqKlpaWpqKipqamqqamtra22tra3tra5ubm6urq7u7u+vr6/vr7Av7/CwMDCwsLDwsLEw8PFxMTFxcXHx8fLysrOzs7R0NDU09PV1dXW1dXc29ve3t7f39/g4ODi4uLj4uLk5OTl5eXo5+fp6Ojp6enq6enr6urr6+vs7Ozy8vL19fX5+fn6+vr8/Pz9/f39/v7+/v7///+14iZxAAAC9ElEQVR4Ae3Z+VPjNhTA8eD1eqNNLR9eu3pv783SEG5COQjhKAchhIYWSGkgJKD//4/oJMzQciRW/ERnOvX3V3nmM/IoijTOyH+n1Emd1Ele6qRO6lwflMYj3/Wj8dLB9Ys5+99ecy8UAoQIPf762/6LOL94dgj4dxDa3rZ2588POYGPE7kPTb3OhhHhc0VGRaez8Abw+eDNgj5nOgc4KMhN63KW3uKw3i7pcWoGDs+oaXFMiHHA1OFM+RiXP0V3WhmML9MiO8VAwQmKVKczBgoOjHWIzoaDKjkbRCcfKjlhnuhYoOSARXMuGKrFLkhO1VZ07CrJ2XYUHWeb5JRdRcctk5w1ZWeN5Gwqv7fN/8Q6OFNe12ckR5qgxIApac4XxX3nC9GpKO6jFaLTNkCBAaNNdGRB6X+uIKlO0wCF6TTJjpxQOIdMSLpzEzshMG40OLL6KsZ5VdVz7l3MDWVyi7rO8ZM2DFTAntR3L5nLwiAmO6fznlUedM8aK0tNTm22/zMStniiCFv80RucrZGddt6yv8te6zaPHtyDI26v90e+21a+TXO2zADRE5ey184ni/sR9Ip8bn3akb0uhYcYmFsU5yfWn0Fkrcp+l3vzn13+A3c/z+/d2XLVivqzY4XEzlXg4V3A8VA+1yFywLu88CqZc2pGeF/E3u/Kx+2+Z/98xDxN4tQN8XAJc6tQOb8fPq8ULP5wsQujPrrTyAA+CkKHmfC1MFX4CiZzwqcPZBqjOq0BuzSIKAzCSAwYNVqjOV1bYJKE3R3J+RhgsoKPozgljknjP6s7vxmQ2AHjd2Xn3Y+YvOidqrPCkRJfUXM6FpAcsDpKTslDWl5JxelaQHTA6io4yy5Sc5cVHE+QHeHFO3WG9Fg91pnxkZ4/E+s4AukJJ845YagjdhLjlF0tjluOcYqBFicoxjiB0OKIYLhzy0CLA+x2qNPMop6yzaGONDS9N0MOd45MlqPHzKPY/a1x/Cu140b6/TR1Uid1Uid1/lfOX467WP+MnlEKAAAAAElFTkSuQmCC");
        }

        ModelAndView modelAndView = new ModelAndView("profile.html",
                "personForm",
                personForm);



        return  modelAndView;
    }

    @PostMapping(path = "profile/photo")
    public void upload(@RequestParam(value = "fileDialog", required = false) MultipartFile file, HttpSession session){

        byte[] bytes = null;
        try {
            bytes = file.getBytes();

        } catch (IOException e) {
            e.printStackTrace();
        }
        session.setAttribute(PROFILE_IMAGE_FILENAME,file.getOriginalFilename());
        session.setAttribute(PROFILE_IMAGE,bytes);

    }

    @PostMapping(path = "/profile")
    public ModelAndView save(@RequestParam("fileDialog") MultipartFile file, @Valid PersonForm personForm,
                             BindingResult result, HttpSession session){

        Login l = (Login)session.getAttribute(LoginController.USER_LOGIN_SESSION);

        Person personDatabase = service.get(l.getPersonId());

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
            result.addError(new FieldError(result.getObjectName(),"password", messageSource.getMessage("error.password.mismatch",null, LocaleContextHolder.getLocale())));
            result.addError(new FieldError(result.getObjectName(),"rePassword",messageSource.getMessage("error.password.mismatch",null, LocaleContextHolder.getLocale())));
            personForm.setPassword(null);
            personForm.setRePassword(null);
        }



        if((!(result.getErrorCount()==4 && !passwordUpdated) && result.hasErrors())){
            return new ModelAndView("profile.html",
                    "personForm",
                    personForm.getId());
        }

        PersonAdapter adapter = new PersonAdapter();

        Person person = adapter.toPerson(personForm);



        person.setId(personDatabase.getId());

        person.setPersonTypeId(personDatabase.getPersonTypeId());

        Image image = new Image();
        if(session.getAttribute(PROFILE_IMAGE_FILENAME)!=null) {

            image.setImage((byte[]) session.getAttribute(PROFILE_IMAGE));
            image.setName((String) session.getAttribute(PROFILE_IMAGE_FILENAME));
            image.setId(null);

        }else{
            if(person.getImage() ==null)
                image = imageService.getDefaultProfileImage();
        }
        person.setImage(image);

        if(passwordUpdated) service.save(person,loginService);
        else service.save(person);


        return new ModelAndView("redirect:/");

    }

}
