package com.happyweekend.service;

import com.happyweekend.connection.ConnectionManager;
import com.happyweekend.dao.PersonActivityCommentDao;
import com.happyweekend.dao.PersonDao;
import com.happyweekend.models.PersonActivityComment;
import com.happyweekend.service.interfaces.IPersonActivityCommentService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PersonActivityCommentService implements IPersonActivityCommentService {
    @Override
    public PersonActivityComment get(int id) {
        Connection con = ConnectionManager.getInstance().connect();
        try {
            PersonActivityCommentDao dao = new PersonActivityCommentDao(con);
            return dao.get(id);
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<PersonActivityComment> getByActivity(int id) {
        Connection con = ConnectionManager.getInstance().connect();
        try {
            PersonActivityCommentDao dao = new PersonActivityCommentDao(con);
            PersonDao personDao = new PersonDao(con);
            PersonService personService = new PersonService();
            List<PersonActivityComment> res = dao.getByActivity(id);
            res.stream().forEach(x -> x.setPerson(personService.get(x.getPersonId())));
            return res;
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void save(PersonActivityComment comment) {
        Connection con = ConnectionManager.getInstance().connect();
        PersonActivityCommentDao dao = new PersonActivityCommentDao(con);
        dao.save(comment);
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
