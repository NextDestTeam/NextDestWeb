package com.happyweekend.spring.controllers;

import com.happyweekend.enumarator.PersonTypeEnum;
import com.happyweekend.models.*;
import com.happyweekend.models.Image;
import com.happyweekend.service.*;
import com.happyweekend.spring.form.ActivityForm;
import com.happyweekend.spring.form.ActivitySearchForm;
import com.happyweekend.spring.form.PersonActivityCommentForm;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Controller
public class ActivityController {


    private static final String FORM_NAME = "activityForm";
    private static final String ACTIVITY_IMAGE = "activityImage";
    private static final String ACTIVITY_IMAGE_FILENAME = "activityImageFileName";


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
        session.setAttribute(ACTIVITY_IMAGE,null);
        session.setAttribute(ACTIVITY_IMAGE_FILENAME,null);

        return new ModelAndView("activity_form.html",FORM_NAME,form);
    }


    @GetMapping(path="activity/id/{id}")
    public ModelAndView load(@PathVariable int id, HttpSession session){
        Activity activity = service.get(id);

        if(activity!=null){
            ActivityForm form = new ActivityForm(activity);
            form.setComments(commentService.getByActivity(id));
            form.setActivityTypeList(activityTypeService.getActivityTypes());
            form.setReactions(reactionService.getAllByActivity(id));
            form.setBadReaction((int) form.getReactions().stream().filter(x->x.getReaction().equals("N")).count());
            form.setMorelessReaction((int) form.getReactions().stream().filter(x->x.getReaction().equals("M")).count());
            form.setGoodReaction((int) form.getReactions().stream().filter(x->x.getReaction().equals("I")).count());
            //form.setImageBytes(loadImage(form.getImageName()));
            form.setImageBytes(form.getImageBytes());
            ModelAndView modelAndView = new ModelAndView("activity_form.html",FORM_NAME,form);

            if(activity.getImageId()!=null) {
                session.setAttribute(ACTIVITY_IMAGE_FILENAME, activity.getImage().getName());
                session.setAttribute(ACTIVITY_IMAGE, activity.getImage().getImage());
            }

            Login l = (Login)session.getAttribute(LoginController.USER_LOGIN_SESSION);

            PersonActivityCommentForm commentForm = new PersonActivityCommentForm();
            commentForm.setPersonId(l.getPersonId());
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
    public ModelAndView search(ActivitySearchForm form, BindingResult bindingResult,HttpSession session){

        Login l = (Login) session.getAttribute(LoginController.USER_LOGIN_SESSION);
        form.setPersonId(l.getPersonId());
        ModelAndView mav = new ModelAndView("search_activity.html");
        mav.addObject("results",service.search(form));
        mav.addObject("activitySearch",form);
        return mav;

    }


    public List<ActivityForm> getUserActivities(Person p){
        List<Activity> activities = service.getActivities();
        List<ActivityForm> list = new ArrayList<>();
        ImageService imageService = new  ImageService();
        activities.stream().filter(x->x.getPersonId()==p.getId() || p.getPersonTypeId() == PersonTypeEnum.ADM.getValue()).forEach(
                a->{
                    if(a.getImageId()>0 && a.getImageId()!=null) {

                        a.setImage(imageService.get(a.getImageId()));
                        a.setImageId(a.getImage().getId());
                    }
                    ActivityForm form = new ActivityForm(a);
                    form.setComments(commentService.getByActivity(a.getId()));
                    form.setActivityTypeList(activityTypeService.getActivityTypes());
                    form.setReactions(reactionService.getAllByActivity(a.getId()));
                    form.setBadReaction((int) form.getReactions().stream().filter(x->x.getReaction().equals("N")).count());
                    form.setMorelessReaction((int) form.getReactions().stream().filter(x->x.getReaction().equals("M")).count());
                    form.setGoodReaction((int) form.getReactions().stream().filter(x->x.getReaction().equals("I")).count());


                    //form.setImageBytes(loadImage(form.getImageName()));
                    list.add(form);
                }
        );

        return list;
    }

    @PostMapping(path = "activity/photo")
    public void upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpSession session){

        byte[] bytes = null;
        try {
            //bytes = file.getBytes();

            BufferedImage image = ImageIO.read(file.getInputStream());
            int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
            image = resizeImageWithHint(image,type);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image,"jpg",outputStream);
            outputStream.flush();
            bytes = outputStream.toByteArray();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        session.setAttribute(ACTIVITY_IMAGE_FILENAME,file.getOriginalFilename());
        session.setAttribute(ACTIVITY_IMAGE,bytes);

    }

    private BufferedImage resizeImageWithHint(BufferedImage originalImage, int type){

        BufferedImage resizedImage = new BufferedImage(640, 380, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 640, 380, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

    @PostMapping(path = "activity")
    public ModelAndView save(
            @ModelAttribute(FORM_NAME) @Valid ActivityForm form,BindingResult result,
            HttpSession session){


        if(result.hasErrors()){
            form.setActivityTypeList(activityTypeService.getActivityTypes());
            return new ModelAndView("activity_form.html",FORM_NAME,form);
        }
        form.setPersonId(((Login)session.getAttribute(LoginController.USER_LOGIN_SESSION)).getPersonId());
        if(session.getAttribute(ACTIVITY_IMAGE_FILENAME)!=null) {
            form.setImageName(session.getAttribute(ACTIVITY_IMAGE_FILENAME).toString());

            service.save(formToModel(form, (byte[]) session.getAttribute(ACTIVITY_IMAGE)));
        }else{
            service.save(formToModel(form,null));
        }
        

        return new ModelAndView("redirect:/");
    }

    @PostMapping(path = "/activity/comment")
    public String comment(PersonActivityCommentForm form, BindingResult result,HttpSession session){
        if(result.hasErrors()){
            return "redirect:/activity/id/"+form.getActivityId();
        }
        Login l = (Login) session.getAttribute(LoginController.USER_LOGIN_SESSION);
        form.setPersonId(l.getPersonId());
        commentService.save(form.toModel());
        return "redirect:/activity/id/"+form.getActivityId();
    }


    private Activity formToModel(ActivityForm form, byte[] bytes) {

        Activity a = new Activity();
        if(form.getId()!=null)
            a = service.get(form.getId());

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
        //form.getDate().toInstant().atZone(ZoneId.systemDefault()).plusSeconds(1);
        a.setDate(form.getDate());

        a.setStatus(form.getStatus());

        a.setImageId(form.getImageId());

        //Creating Image to put in database model
        Image image = new Image();
        image.setName(form.getImageName());
        image.setImage(bytes);
        a.setImage(image);
        return a;
    }


    public List<ActivityForm> searchFullText(ActivitySearchForm form) {

        List<Activity> activities = service.searchFullText(form);
        List<ActivityForm> list = new ArrayList<>();
        ImageService imageService = new  ImageService();
        activities.stream().filter(x->x.getPersonId()==form.getPersonId()).forEach(
                a->{
                    if(a.getImageId()>0 && a.getImageId()!=null) {

                        a.setImage(imageService.get(a.getImageId()));
                        a.setImageId(a.getImage().getId());
                    }
                    ActivityForm f = new ActivityForm(a);
                    f.setComments(commentService.getByActivity(a.getId()));
                    f.setActivityTypeList(activityTypeService.getActivityTypes());
                    f.setReactions(reactionService.getAllByActivity(a.getId()));
                    f.setBadReaction((int) f.getReactions().stream().filter(x->x.getReaction().equals("N")).count());
                    f.setMorelessReaction((int) f.getReactions().stream().filter(x->x.getReaction().equals("M")).count());
                    f.setGoodReaction((int) f.getReactions().stream().filter(x->x.getReaction().equals("I")).count());


                    //form.setImageBytes(loadImage(form.getImageName()));
                    list.add(f);
                }
        );

        return list;
    }
}
