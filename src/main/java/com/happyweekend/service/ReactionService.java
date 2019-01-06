package com.happyweekend.service;

import com.happyweekend.connection.ConnectionManager;
import com.happyweekend.dao.ReactionDao;
import com.happyweekend.models.Reaction;
import com.happyweekend.service.interfaces.IReactionService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ReactionService implements IReactionService {
    @Override
    public Reaction get(int id) {
        Connection con = ConnectionManager.getInstance().connect();
        try {
            return new ReactionDao(con).get(id);
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Reaction> getByActivity(int id) {
        Connection con = ConnectionManager.getInstance().connect();
        List<Reaction> list= new ReactionDao(con).getAll().stream()
                .filter(x->x.getActivity() == id).collect(Collectors.toList());
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Reaction> getAllByActivity(int idActivity){
        Connection con = ConnectionManager.getInstance().connect();
        ReactionDao dao = new ReactionDao(con);

        List<Reaction> list = dao.getAllByActivity(idActivity);
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(Reaction reaction) {
        Connection con= ConnectionManager.getInstance().connect();
        try{
            new ReactionDao(con).save(reaction);
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
