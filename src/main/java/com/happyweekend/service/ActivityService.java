package com.happyweekend.service;

import com.happyweekend.connection.ConnectionManager;
import com.happyweekend.dao.ActivityDao;
import com.happyweekend.models.Activity;
import com.happyweekend.models.ActivityType;
import com.happyweekend.service.interfaces.IActivityService;

import java.util.List;

public class ActivityService implements IActivityService {
    @Override
    public List<Activity> getActivities() {
        ActivityDao dao = new ActivityDao(ConnectionManager.getInstance().connect());
        return dao.getAll();
    }

    @Override
    public void save(Activity activity) {
        ActivityDao dao = new ActivityDao(ConnectionManager.getInstance().connect());
        dao.save(activity);
    }

    @Override
    public Activity get(int id) {
        ActivityDao dao = new ActivityDao(ConnectionManager.getInstance().connect());
        return dao.get(id);
    }

    public List<Activity> get(Activity activity) {
        ActivityDao dao = new ActivityDao(ConnectionManager.getInstance().connect());
        ActivityTypeService activityTypeService = new ActivityTypeService();
        List<ActivityType> activityTypes = activityTypeService.getActivityTypes();
        List<Activity> res = dao.search(activity);

        res.forEach(x->x.setActivityType(
                activityTypes.stream().filter(y->y.getId()==x.getActivityTypeId()).findFirst().get()
        ));
        return res;
    }
}
