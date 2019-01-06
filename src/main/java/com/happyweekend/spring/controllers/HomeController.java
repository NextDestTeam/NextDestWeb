package com.happyweekend.spring.controllers;

import com.happyweekend.models.Activity;
import com.happyweekend.models.Login;
import com.happyweekend.models.Person;
import com.happyweekend.service.PersonService;
import com.happyweekend.spring.form.ActivityForm;
import com.happyweekend.spring.form.ActivitySearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    ActivityController activityController;

    @RequestMapping(value = "/testResolver", method = RequestMethod.GET)
    public ModelAndView showHome(@RequestParam(value = "viewResolver") Optional<String> viewResolver) {
        return getDefaultView(viewResolver);
    }

    private ModelAndView getDefaultView(Optional<String> viewResolver) {
        ModelAndView model = new ModelAndView(createView(viewResolver, "/home"));
        model.addObject("name", "Spring Squad");
        return model;
    }

    private String createView(Optional<String> viewResolver, String viewName) {
        String result = viewResolver.isPresent() ? viewResolver.get() : "jsp";
        result += viewName;
        return result;
    }

    @RequestMapping("/")
    public ModelAndView index(HttpSession session){

        Login l = (Login) session.getAttribute(LoginController.USER_LOGIN_SESSION);
        Person p = (new PersonService()).get(l.getPersonId());
        List<ActivityForm> activityList = activityController.getUserActivities(p);
        ModelAndView mav = new ModelAndView("index.html","activities",activityList);
        mav.addObject("activitySearch",new ActivitySearchForm());
        return mav;
    }

    @PostMapping(path = "activity/search/fulltext")
    public ModelAndView searchFullText(ActivitySearchForm form, BindingResult bindingResult, HttpSession session){


        Login l = (Login) session.getAttribute(LoginController.USER_LOGIN_SESSION);
        form.setPersonId(l.getPersonId());

        ModelAndView mav = new ModelAndView("index.html");
        mav.addObject("activities",activityController.searchFullText(form));
        mav.addObject("activitySearch",form);
        return mav;

    }
}
