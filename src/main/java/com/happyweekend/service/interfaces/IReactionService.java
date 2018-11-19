package com.happyweekend.service.interfaces;

import com.happyweekend.models.Reaction;

import java.util.List;

public interface IReactionService {
    public Reaction get(int id);
    public List<Reaction> getByActivity(int id);
    public void save(Reaction reaction);

}
