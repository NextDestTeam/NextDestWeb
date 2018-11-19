package com.happyweekend.service;

import com.happyweekend.connection.ConnectionManager;
import com.happyweekend.dao.PersonActivityCommentDao;
import com.happyweekend.dao.PersonDao;
import com.happyweekend.models.PersonActivityComment;
import com.happyweekend.service.interfaces.IPersonActivityCommentService;

import java.sql.Connection;
import java.util.List;

public class PersonActivityCommentService implements IPersonActivityCommentService {
    @Override
    public PersonActivityComment get(int id) {
        Connection con = ConnectionManager.getInstance().connect();
        PersonActivityCommentDao dao = new PersonActivityCommentDao(con);
        return dao.get(id);

    }

    @Override
    public List<PersonActivityComment> getByActivity(int id) {
        Connection con = ConnectionManager.getInstance().connect();
        PersonActivityCommentDao dao = new PersonActivityCommentDao(con);
        PersonDao personDao = new PersonDao(con);
        List<PersonActivityComment> res = dao.getByActivity(id);
        res.stream().forEach(x->x.setPerson(personDao.get(x.getId())));
        return res;

    }

    @Override
    public void save(PersonActivityComment comment) {
        Connection con = ConnectionManager.getInstance().connect();
        PersonActivityCommentDao dao = new PersonActivityCommentDao(con);
        dao.save(comment);
    }
}
