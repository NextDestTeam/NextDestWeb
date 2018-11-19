package com.happyweekend.service;

import com.happyweekend.connection.ConnectionManager;
import com.happyweekend.dao.ReactionDao;
import com.happyweekend.models.Reaction;
import com.happyweekend.service.interfaces.IReactionService;

import java.util.List;
import java.util.stream.Collectors;

public class ReactionService implements IReactionService {
    @Override
    public Reaction get(int id) {
        return new ReactionDao(ConnectionManager.getInstance().connect()).get(id);
    }

    @Override
    public List<Reaction> getByActivity(int id) {
        return new ReactionDao(ConnectionManager.getInstance().connect()).getAll().stream()
                .filter(x->x.getActivity() == id).collect(Collectors.toList());
    }

    @Override
    public void save(Reaction reaction) {
        new ReactionDao(ConnectionManager.getInstance().connect()).save(reaction);
    }
}
