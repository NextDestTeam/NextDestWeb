package com.happyweekend.service.interfaces;


import com.happyweekend.models.Activity;
import com.happyweekend.spring.form.ActivityForm;
import com.happyweekend.spring.form.ActivitySearchForm;

import java.util.List;

public interface IActivityService {
    public List<Activity> getActivities();

    void save(Activity activity);

    Activity get(int id);

    List<Activity> search(ActivitySearchForm form);

    List<Activity> searchFullText(ActivitySearchForm form);
}
