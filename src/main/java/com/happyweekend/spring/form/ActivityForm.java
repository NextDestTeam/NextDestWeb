package com.happyweekend.spring.form;


import com.happyweekend.models.Activity;
import com.happyweekend.models.ActivityType;
import com.happyweekend.models.PersonActivityComment;
import com.happyweekend.models.Reaction;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;


import javax.validation.constraints.Size;
import java.util.Base64;
import java.util.Date;
import java.util.List;


public class ActivityForm {


    private Integer id;
    @NotEmpty
    @Size(max = 500)
    private String name;

    private String character;

    @NotEmpty
    @Size( max = 500)
    private String shortDescription;
    @Size( max = 5000)
    private String description;
    @NotEmpty
    @Size(max = 255)
    private String location;
    private Double price;
    private Integer personId;
    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;
    //@NotNull(message = "{activity.type.null}")
    private ActivityType activityType;
    @NonNull
    private int activityTypeId;

    private List<ActivityType> activityTypeList;
    private String imageName;
    private List<PersonActivityComment> comments;
    private List<Reaction> reactions;
    @Getter@Setter
    private Integer badReaction;
    @Getter@Setter
    private Integer morelessReaction;
    @Getter@Setter
    private Integer goodReaction;
    private String imageBytes;
    private Integer imageId;
    private char status;

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
        this.imageId = activity.getImageId();
        this.name = activity.getName();
        this.location = activity.getLocation();
        this.price = activity.getPrice();
        if(activity.getImage()!=null)
            this.imageBytes = "data:image/png;base64, "+Base64.getEncoder().encodeToString(activity.getImage().getImage());
        this.status = activity.getStatus();
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public void setImageBytes(String imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getImageBytes() {
        return imageBytes;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }
}
