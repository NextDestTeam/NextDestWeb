package com.happyweekend.spring.controllers;

import com.happyweekend.models.Activity;
import com.happyweekend.service.ActivityService;
import com.happyweekend.spring.form.ActivityForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ActivityController {

    ActivityService service;

    @GetMapping(path = "event")
    public ModelAndView index(){

        return new ModelAndView("activity_form.html");
    }

    @PostMapping
    public ModelAndView save(@Validated ActivityForm form, BindingResult result){

        if(result.hasErrors()){
            return new ModelAndView("activity_form.html","activityForm",form);
        }

        service.save(formToModel(form));
        

        return new ModelAndView("redirect:/index.html");
    }

    private Activity formToModel(ActivityForm form) {
        Activity a = new Activity();
        a.setId(form.getId());
        a.setDescription(form.getDescription());
        a.setLocation(form.getDescription());
        //a.setPersonId(fo);
        a.setName(form.getName());
        a.setPrice(form.getPrice());
        return a;
    }


}
