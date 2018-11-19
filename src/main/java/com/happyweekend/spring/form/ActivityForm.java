package com.happyweekend.spring.form;


import com.happyweekend.models.Activity;
import com.happyweekend.models.ActivityType;
import com.happyweekend.models.PersonActivityComment;
import com.happyweekend.models.Reaction;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class ActivityForm {


    private Integer id;
    @NotEmpty
    private String name;

    private String character;

    @NotEmpty
    private String shortDescription;
    private String description;
    @NotEmpty
    private String location;
    private Integer price;
    private Integer personId;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;
    //@NotNull(message = "{activity.type.null}")
    private ActivityType activityType;
    @NotNull
    private int activityTypeId;



    private List<ActivityType> activityTypeList;
    private String imageName;
    private List<PersonActivityComment> comments;
    private List<Reaction> reactions;
    private byte[] imageBytes;

    public ActivityForm(){

    }

    public ActivityForm(Activity activity) {
        this.activityType = activity.getActivityType();
        this.activityTypeId = activity.getActivityTypeId();
        this.date = activity.getDate();
        this.description = activity.getDescription();
        this.shortDescription = activity.getShortDescription();
        this.id = activity.getId();
        this.personId = activity.getPersonId();
        this.imageName = activity.getImage();
        this.name = activity.getName();
        this.location = activity.getLocation();
        this.price = activity.getPrice();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ActivityType> getActivityTypeList() {
        return activityTypeList;
    }

    public void setActivityTypeList(List<ActivityType> activityTypeList) {
        this.activityTypeList = activityTypeList;
    }

    public int getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(int activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setComments(List<PersonActivityComment> comments) {
        this.comments = comments;
    }

    public List<PersonActivityComment> getComments() {
        return comments;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }
}
