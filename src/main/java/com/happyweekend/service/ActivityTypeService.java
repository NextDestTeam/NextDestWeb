package com.happyweekend.service;

import com.happyweekend.connection.ConnectionManager;
import com.happyweekend.dao.ActivityTypeDao;
import com.happyweekend.models.ActivityType;
import com.happyweekend.service.interfaces.IActivityTypeService;

import java.util.List;

public class ActivityTypeService implements IActivityTypeService {
    @Override
    public List<ActivityType> getActivityTypes() {
        ActivityTypeDao dao = new ActivityTypeDao(ConnectionManager.getInstance().connect());
        return dao.getAll();
    }

    @Override
    public void save(ActivityType activityType) {

    }
}
