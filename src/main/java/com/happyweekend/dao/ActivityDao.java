package com.happyweekend.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.happyweekend.models.Activity;
import com.happyweekend.spring.form.ActivitySearchForm;

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
        activity.setPrice(rs.getDouble("price"));
        activity.setActivityTypeId(rs.getInt("activity_type"));
        activity.setDate(rs.getTimestamp("date"));
        activity.setImageId(rs.getInt("image_id"));

        return activity;
    }

    @Override
	public List<Activity> getAll() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Activity> activities = new ArrayList<>();
		ResultSet rs;
		String query = "SELECT * FROM ACTIVITY WHERE "+
				" date>= '"+sdf.format(new java.util.Date())+"'::date ORDER BY date asc";
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
				activity.setPrice(rs.getDouble("price"));
				activity.setActivityTypeId(rs.getInt("activity_type"));
                activity.setDate(rs.getDate("date"));
                activity.setImageId(rs.getInt("image_id"));
				activity.setStatus(rs.getString("status").charAt(0));
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
					 + t.getActivityTypeId()+"',"
                     + String.format("TIMESTAMP '%s'",sdf.format(t.getDate()))+", "
					+ (t.getImageId() == null?null:t.getImageId() )+")";
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
		String query = "UPDATE ACTIVITY SET"
					 + "name='" + t.getName() + "', "
					 + "short_description='" + t.getShortDescription() + "', "
					 + "description='" + t.getDescription() + "', "
					 + "location='" + t.getLocation() + "', "
					 + "price='" + t.getPrice() + "', "
					 + "person_id='" + t.getPersonId() +"',"
                     + "activity_type='"+t.getActivityType().getId()+ "', "
                     + "date = TIMESTAMP '"+sdf.format(t.getDate())+ "', "
                     + "image_id = '"+t.getImageId()+ "', "
				     + "status = "+"'"+t.getStatus()+"'"
				     + "WHERE ID = "+t.getId();
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

	public List<Activity> search(ActivitySearchForm form) {

		ResultSet rs;
		String query = "SELECT id, name, short_description, description, location, price, person_id, \n" +
				"       activity_type, date,image_id\n" +
				"  FROM activity\n" +
				"  where name like '%"+
				(form.getName()==null?"":form.getName())+
				"%' and short_description like '%"+
				(form.getShortDescription() == null?"":form.getShortDescription())+"%'"+
				" and description like '%"+(form.getDescription()==null?"":form.getDescription())+"%'"+
				" and location like '%"+(form.getLocation()==null?"":form.getLocation())+"%'"+
				" and person_id = "+(form.getPersonId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(form.getIniDate()!=null) {
			java.util.Date d = Date.from(form.getIniDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
			query+=" and date >= '" + sdf.format(d) + "'::date";
		}
		if(form.getEndDate()!=null) {
			java.util.Date d = Date.from(form.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
			query+=" and date <= '" + sdf.format(d) + "'::date";
		}
		Statement stm;
		List<Activity> result = new ArrayList<>();
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			while(rs.next()) {
				Activity activity = toModel(rs);
				result.add(activity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Activity> searchFullText(ActivitySearchForm form) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet rs;
		String query = "SELECT id, name, short_description, description, location, price, person_id, \n" +
				"       activity_type, date,image_id\n" +
				"  FROM activity\n" +
				"  where "+
				" date>= '"+sdf.format(new java.util.Date())+"'::date "+
				"  and (name like '%"+
				(form.getName()==null?"":form.getName())+
				"%' or short_description like '%"+
				(form.getName() == null?"":form.getName())+"%'"+
				" or description like '%"+(form.getName()==null?"":form.getName())+"%'"+
				" or location like '%"+(form.getName()==null?"":form.getName())+"%'"+
				" and person_id = "+(form.getPersonId());

		if(form.getIniDate()!=null) {
			java.util.Date d = Date.from(form.getIniDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
			query+=" and date >= '" + sdf.format(d) + "'::date";
		}
		if(form.getEndDate()!=null) {
			java.util.Date d = Date.from(form.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
			query+=" and date <= '" + sdf.format(d) + "'::date";
		}
		query+=") order by date asc";
		Statement stm;
		List<Activity> result = new ArrayList<>();
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			while(rs.next()) {
				Activity activity = toModel(rs);
				result.add(activity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
