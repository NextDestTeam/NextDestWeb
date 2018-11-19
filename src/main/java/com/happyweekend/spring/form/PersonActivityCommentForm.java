package com.happyweekend.spring.form;

import com.happyweekend.models.Person;
import com.happyweekend.models.PersonActivityComment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PersonActivityCommentForm {
    private Integer id;
    @NotBlank
    private String comment;
    @NotNull
    private Integer personId;
    @NotNull
    private Integer activityId;
    private Person person;

    public PersonActivityComment toModel(){

        PersonActivityComment model  = new PersonActivityComment();
        model.setActivityId(activityId);
        model.setPersonId(personId);
        model.setComment(comment);
        model.setId(id);

        return model;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
