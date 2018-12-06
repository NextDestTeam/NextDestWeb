package com.happyweekend.service;

import com.happyweekend.connection.ConnectionManager;
import com.happyweekend.dao.ActivityTypeDao;
import com.happyweekend.models.ActivityType;
import com.happyweekend.service.interfaces.IActivityTypeService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ActivityTypeService implements IActivityTypeService {
    @Override
    public List<ActivityType> getActivityTypes() {
        Connection con = ConnectionManager.getInstance().connect();
        try {
            ActivityTypeDao dao = new ActivityTypeDao(con);
            return dao.getAll();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void save(ActivityType activityType) {

    }
}
