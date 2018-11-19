package com.happyweekend.service.interfaces;

import com.happyweekend.models.PersonActivityComment;

import java.util.List;

public interface IPersonActivityCommentService {

    public PersonActivityComment get(int id);
    public List<PersonActivityComment> getByActivity(int id);
    public void save(PersonActivityComment comment);
}
