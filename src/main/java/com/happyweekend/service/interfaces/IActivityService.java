package com.happyweekend.service.interfaces;


import com.happyweekend.models.Activity;

import java.util.List;

public interface IActivityService {
    public List<Activity> getActivities();

    void save(Activity activity);
}
