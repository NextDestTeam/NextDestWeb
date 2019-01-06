package com.happyweekend.spring.form;

import com.happyweekend.models.Activity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class ActivitySearchForm {
    @Setter@Getter
    private Integer personId;
    private List<Activity> activityList;
    private String name;
    private String shortDescription;
    @Getter@Setter
    private String description;
    private int activityTypeId;
    @Getter@Setter
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate iniDate;
    @Getter@Setter
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
    private String location;


    public int getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(int activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
