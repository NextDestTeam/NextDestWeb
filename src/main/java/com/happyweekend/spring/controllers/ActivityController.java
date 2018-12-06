package com.happyweekend.spring.controllers;

import com.happyweekend.models.Activity;
import com.happyweekend.models.Image;
import com.happyweekend.models.Login;
import com.happyweekend.service.*;
import com.happyweekend.spring.form.ActivityForm;
import com.happyweekend.spring.form.ActivitySearchForm;
import com.happyweekend.spring.form.PersonActivityCommentForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Controller
public class ActivityController {


    private static final String FORM_NAME = "activityForm";

    ActivityService service = new ActivityService();
    ActivityTypeService activityTypeService = new ActivityTypeService();
    PersonActivityCommentService commentService = new PersonActivityCommentService();
    ReactionService reactionService = new ReactionService();
    PersonService personService = new PersonService();


    @GetMapping(path = "activity")
    public ModelAndView index(HttpSession session){

        ActivityForm form = new ActivityForm();
        form.setActivityTypeList(activityTypeService.getActivityTypes());
        form.setPersonId(((Login)session.getAttribute(LoginController.USER_LOGIN_SESSION)).getPersonId());

        return new ModelAndView("activity_form.html",FORM_NAME,form);
    }


    @GetMapping(path="activity/id/{id}")
    public ModelAndView load(@PathVariable int id){
        Activity activity = service.get(id);
        if(activity!=null){
            ActivityForm form = new ActivityForm(activity);
            form.setComments(commentService.getByActivity(id));
            form.setActivityTypeList(activityTypeService.getActivityTypes());
            form.setReactions(reactionService.getByActivity(id));
            //form.setImageBytes(loadImage(form.getImageName()));
            form.setImageBytes(form.getImageBytes());
            ModelAndView modelAndView = new ModelAndView("activity_form.html",FORM_NAME,form);
            //TODO get peson id
            PersonActivityCommentForm commentForm = new PersonActivityCommentForm();
            commentForm.setPersonId(1);
            commentForm.setActivityId(id);

            modelAndView.addObject("activityComment",commentForm);
            return modelAndView;
        }
        return new ModelAndView("error");
    }

    @GetMapping(path = "activity/search")
    public ModelAndView search(){


        return new ModelAndView("search_activity.html","activitySearch",new ActivitySearchForm());
    }

    @PostMapping(path = "activity/search")
    public ModelAndView search(ActivitySearchForm form, BindingResult bindingResult){

        Activity a = new Activity();

        a.setName(form.getName());
        a.setShortDescription(form.getShortDescription());

        ModelAndView mav = new ModelAndView("search_activity.html");
        mav.addObject("results",service.get(a));
        mav.addObject("activitySearch",form);
        return mav;

    }

    public List<ActivityForm> getUserActivities(){
        List<Activity> activities = service.getActivities();
        List<ActivityForm> list = new ArrayList<>();
        ImageService imageService = new  ImageService();
        for(Activity a : activities) {

            if(a.getImageId()>0 && a.getImageId()!=null) {

                a.setImage(imageService.get(a.getId()));
                a.setImageId(a.getImage().getId());
            }
            ActivityForm form = new ActivityForm(a);


            //form.setImageBytes(loadImage(form.getImageName()));
            list.add(form);
        }
        return list;
    }

    @PostMapping(path = "activity")
    public ModelAndView save(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute(FORM_NAME) @Valid ActivityForm form,
            HttpSession session,
            BindingResult result){

        boolean fileOk = false;

        form.setImageName(file.getOriginalFilename());

        byte[] bytes = null;
        try {
            bytes = file.getBytes();
            fileOk = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(result.hasErrors() || !fileOk){
            form.setActivityTypeList(activityTypeService.getActivityTypes());
            return new ModelAndView("activity_form.html",FORM_NAME,form);
        }
        form.setPersonId(((Login)session.getAttribute(LoginController.USER_LOGIN_SESSION)).getPersonId());


        service.save(formToModel(form,bytes));
        

        return new ModelAndView("redirect:/");
    }

    @PostMapping(path = "/activity/comment")
    public String comment(PersonActivityCommentForm form, BindingResult result){
        if(result.hasErrors()){
            return "redirect:";
        }
        //TODO set personID
        form.setPersonId(1);
        commentService.save(form.toModel());
        return "redirect:";
    }


    private Activity formToModel(ActivityForm form, byte[] bytes) {
        Activity a = new Activity();
        a.setId(form.getId());
        a.setDescription(form.getDescription());
        a.setLocation(form.getLocation());
        a.setShortDescription(form.getShortDescription());

        a.setPersonId(form.getPersonId());
        a.setActivityType(activityTypeService.getActivityTypes().stream()
                .filter(x->x.getId()==form.getActivityTypeId()).findFirst().get());
        a.setActivityTypeId(form.getActivityTypeId());
        a.setName(form.getName());
        a.setPrice(form.getPrice());
        a.setDate(form.getDate());
        a.setImageId(form.getImageId());

        //Creating Image to put in database model
        Image image = new Image();
        image.setName(form.getImageName());
        image.setImage(bytes);
        a.setImage(image);
        return a;
    }


}
