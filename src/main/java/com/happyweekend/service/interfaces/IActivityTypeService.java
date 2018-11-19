package com.happyweekend.service.interfaces;

import com.happyweekend.models.ActivityType;

import java.util.List;

public interface IActivityTypeService {
    public List<ActivityType> getActivityTypes();
    public void save(ActivityType activityType);
}
