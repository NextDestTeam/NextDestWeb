package com.happyweekend.spring.controllers;

import com.happyweekend.models.Activity;
import com.happyweekend.models.PersonActivityComment;
import com.happyweekend.service.*;
import com.happyweekend.spring.form.ActivityForm;
import com.happyweekend.spring.form.ActivitySearchForm;
import com.happyweekend.spring.form.PersonActivityCommentForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.*;
import java.util.Date;


@Controller
public class ActivityController {


    private static final String FORM_NAME = "activityForm";
    private static final String IMAGES_PATH = "/home/lucas/workspace/NextDestApp/NextDestWeb/activity_img/";

    ActivityService service = new ActivityService();
    ActivityTypeService activityTypeService = new ActivityTypeService();
    PersonActivityCommentService commentService = new PersonActivityCommentService();
    ReactionService reactionService = new ReactionService();
    PersonService personService = new PersonService();


    @GetMapping(path = "activity")
    public ModelAndView index(){

        ActivityForm form = new ActivityForm();
        form.setActivityTypeList(activityTypeService.getActivityTypes());

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
            form.setImageBytes(loadImage(form.getImageName()));
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

    private byte[] loadImage(String imageName) {

        try {
            File serverFile = new File(IMAGES_PATH+ imageName);
            FileInputStream fileInputStream = null;

                fileInputStream = new FileInputStream(serverFile);

            byte[]  bytes = new byte[(int)serverFile.length()];

            fileInputStream.read(bytes);
        return bytes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(path = "activity")
    public ModelAndView save(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute(FORM_NAME) @Valid ActivityForm form,
            BindingResult result){

        if(result.hasErrors() || !checkFile(file,form)){
            form.setActivityTypeList(activityTypeService.getActivityTypes());
            return new ModelAndView("activity_form.html",FORM_NAME,form);
        }

        service.save(formToModel(form));
        

        return new ModelAndView("redirect:/index.html");
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

    private boolean checkFile(MultipartFile file, ActivityForm form) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                File dir = new File(IMAGES_PATH);
                if (!dir.exists())
                    dir.mkdirs();

                String newName = String.valueOf((new Date()).getTime())+
                        file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());

                form.setImageName(newName);
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + newName);

                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();


                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }


    private Activity formToModel(ActivityForm form) {
        Activity a = new Activity();
        a.setId(form.getId());
        a.setDescription(form.getDescription());
        a.setLocation(form.getLocation());
        a.setShortDescription(form.getShortDescription());

        //TODO GET PERSON FROM SESSION
        a.setPersonId(1);
        a.setActivityType(activityTypeService.getActivityTypes().stream()
                .filter(x->x.getId()==form.getActivityTypeId()).findFirst().get());
        a.setActivityTypeId(form.getActivityTypeId());
        a.setName(form.getName());
        a.setPrice(form.getPrice());
        a.setDate(form.getDate());
        a.setImage(form.getImageName());
        return a;
    }


}
