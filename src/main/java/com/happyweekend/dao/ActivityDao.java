package com.happyweekend.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.happyweekend.models.Activity;

//TODO Change to hibernate or use parameters in the prepared statements


public class ActivityDao implements Dao<Activity>{
	private Connection connection;

	public ActivityDao(Connection connection) {
		this.connection = connection;
	}


    public List<Activity> search(Activity activity) {

        ResultSet rs;
        String query = "SELECT id, name, short_description, description, location, price, person_id, \n" +
                "       activity_type, date,image_id\n" +
                "  FROM activity\n" +
                "  where name like '%"+
                (activity.getName()==null?"":activity.getName())+
                "%' and short_description like '%"+
                (activity.getShortDescription() == null?"":activity.getShortDescription())+"%'";
        Statement stm;
        List<Activity> result = new ArrayList<>();
        try {
            stm = this.connection.createStatement();
            rs = stm.executeQuery(query);
            while(rs.next()) {
                activity = toModel(rs);
                result.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

	@Override
	public Activity get(Integer id) {
		Activity activity = new Activity();
		ResultSet rs;
		String query = "SELECT * FROM ACTIVITY WHERE ID='" + id + "'";
		Statement stm;
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			rs.next();
            activity = toModel(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return activity;
	}

    private Activity toModel(ResultSet rs) throws SQLException {
	    Activity activity = new Activity();

        activity.setId(rs.getInt("id"));
        activity.setName(rs.getString("name"));
        activity.setLocation(rs.getString("location"));
        activity.setDescription(rs.getString("description"));
        activity.setPersonId(rs.getInt("person_id"));
        activity.setShortDescription(rs.getString("short_description"));
        activity.setPrice(rs.getInt("price"));
        activity.setActivityTypeId(rs.getInt("activity_type"));
        activity.setDate(rs.getDate("date"));
        activity.setImageId(rs.getInt("image_id"));

        return activity;
    }

    @Override
	public List<Activity> getAll() {
		List<Activity> activities = new ArrayList<>();
		ResultSet rs;
		String query = "SELECT * FROM ACTIVITY";
		Statement stm;
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			while(rs.next()) {
				Activity activity = new Activity();
				activity.setId(rs.getInt("id"));
				activity.setName(rs.getString("name"));
				activity.setLocation(rs.getString("location"));
				activity.setDescription(rs.getString("description"));
				activity.setPersonId(rs.getInt("person_id"));
				activity.setShortDescription(rs.getString("short_description"));
				activity.setPrice(rs.getInt("price"));
				activity.setActivityTypeId(rs.getInt("activity_type"));
                activity.setDate(rs.getDate("date"));
                activity.setImageId(rs.getInt("image_id"));
				activities.add(activity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return activities;
	}

	@Override
	public void save(Activity t) {
		Statement stm;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String query = "INSERT INTO ACTIVITY(id, name, short_description, description, location, price, person_id,activity_type,date,image_id)"
					 + "values(default, '"
					 + t.getName() + "', '"
					 + t.getShortDescription() + "', '"
					 + t.getDescription() + "', '"
					 + t.getLocation() + "', '"
					 + t.getPrice() + "', '"
					 + t.getPersonId() + "', '"
					 + t.getActivityTypeId()+ "', TIMESTAMP "
                     + String.format("'%s',",sdf.format(t.getDate()))+
					+ t.getImageId()+")";
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void update(Activity t) {
		Statement stm;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String query = "UPDATE ACTIVITY SET("
					 + "id='" + t.getId() + "', "
					 + "name='" + t.getName() + "', "
					 + "short_description='" + t.getShortDescription() + "', "
					 + "description='" + t.getDescription() + "', "
					 + "location='" + t.getLocation() + "', "
					 + "price='" + t.getPrice() + "', "
					 + "person_id='" + t.getPersonId() +"',"
                     + "activityType='"+t.getActivityType().getId()+ "', "
                     + "date = TIMESTAMP '"+sdf.format(t.getDate())+ "', "
                     + "image_id = '"+t.getImageId()+ "')";
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}				
	}

	@Override
	public void delete(Activity t) {
		Statement stm;
		String query = "delete from activity "
					 + "where id='" + t.getId() + "'";	
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

}
