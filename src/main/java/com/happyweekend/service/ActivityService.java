package com.happyweekend.service;

import com.happyweekend.connection.ConnectionManager;
import com.happyweekend.dao.ActivityDao;
import com.happyweekend.dao.ImageDao;
import com.happyweekend.models.Activity;
import com.happyweekend.models.ActivityType;
import com.happyweekend.models.Image;
import com.happyweekend.service.interfaces.IActivityService;
import com.happyweekend.service.interfaces.IImageService;
import com.happyweekend.spring.form.ActivitySearchForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ActivityService implements IActivityService {
    @Override
    public List<Activity> getActivities() {
        Connection con = ConnectionManager.getInstance().connect();
        ActivityDao dao = new ActivityDao(con);
        List<Activity> list = dao.getAll();
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    @Override
    public void save(Activity activity) {
        Connection con = ConnectionManager.getInstance().connect();
        try {
            try {
                if(activity.getImage()!=null){
                    IImageService imageService = new ImageService();
                    imageService.save(activity.getImage());

                    //Updating activity image reference
                    activity.setImageId(activity.getImage().getId());
                }

                ActivityDao dao = new ActivityDao(con);
                if(activity.getId()==null)
                    dao.save(activity);
                else
                    dao.update(activity);

            } finally {
                con.close();
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public Activity get(int id) {
        Connection con = ConnectionManager.getInstance().connect();
        try {
            ActivityDao dao = new ActivityDao(con);

            Activity activity = dao.get(id);
            if(activity.getId()>0 && activity.getImageId()!=null){
                ImageDao imageDao = new ImageDao(con);
                Image image = imageDao.get(activity.getImageId());
                activity.setImageId(image.getId());
                activity.setImage(image);
            }
            return activity;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public List<Activity> search(ActivitySearchForm form) {
        Connection con = ConnectionManager.getInstance().connect();
        try {
            ActivityDao dao = new ActivityDao(con);
            ActivityTypeService activityTypeService = new ActivityTypeService();
            List<ActivityType> activityTypes = activityTypeService.getActivityTypes();
            List<Activity> res = dao.search(form);

            res.forEach(x -> x.setActivityType(
                    activityTypes.stream().filter(y -> y.getId() == x.getActivityTypeId()).findFirst().get()
            ));
            con.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Activity> searchFullText(ActivitySearchForm form) {
        Connection con = ConnectionManager.getInstance().connect();
        try {
            ActivityDao dao = new ActivityDao(con);
            ActivityTypeService activityTypeService = new ActivityTypeService();
            List<ActivityType> activityTypes = activityTypeService.getActivityTypes();
            List<Activity> res = dao.searchFullText(form);

            res.forEach(x -> x.setActivityType(
                    activityTypes.stream().filter(y -> y.getId() == x.getActivityTypeId()).findFirst().get()
            ));
            con.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Activity> get(Activity activity) {
        Connection con = ConnectionManager.getInstance().connect();
        try {
            ActivityDao dao = new ActivityDao(con);
            ActivityTypeService activityTypeService = new ActivityTypeService();
            List<ActivityType> activityTypes = activityTypeService.getActivityTypes();
            List<Activity> res = dao.search(activity);

            res.forEach(x -> x.setActivityType(
                    activityTypes.stream().filter(y -> y.getId() == x.getActivityTypeId()).findFirst().get()
            ));
            con.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}
