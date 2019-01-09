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
                "       activity_type, date,image_id,status\n" +
                "  FROM activity\n" +
                "  where name like '%"+
                (activity.getName()==null?"":activity.getName())+
                "%' and short_description like '%"+
                (activity.getShortDescription() == null?"":activity.getShortDescription())+"%'";
        PreparedStatement stm;
        List<Activity> result = new ArrayList<>();
        try {
            stm = this.connection.prepareStatement(query);
			stm.setString(1,activity.getName());
			stm.setString(2,activity.getShortDescription());

            rs = stm.executeQuery();
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
		String query = "SELECT * FROM ACTIVITY WHERE ID=?";
		PreparedStatement stm;
		try {
			stm = this.connection.prepareStatement(query);
			stm.setInt(1,id);
			rs = stm.executeQuery();
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
		activity.setStatus(rs.getString("status").charAt(0));

        return activity;
    }

    @Override
	public List<Activity> getAll() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Activity> activities = new ArrayList<>();
		ResultSet rs;
		String query = "SELECT * FROM ACTIVITY WHERE "+
				" date>= ? ORDER BY date asc";
		PreparedStatement stm;
		try {
			stm = this.connection.prepareStatement(query);
			stm.setDate(1,new Date(new java.util.Date().getTime()));
			rs = stm.executeQuery();
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
		PreparedStatement stm;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String query = "INSERT INTO ACTIVITY(id, name, short_description, description, location, price, person_id,activity_type,date,image_id,status)"
					 + "values(default, ?,?,?,?,?,?,?,?,?,?)";
		try {
			stm = this.connection.prepareStatement(query);
			stm.setString(1, t.getName());
			stm.setString(2,t.getShortDescription());
			stm.setString(3, t.getDescription());
			stm.setString(4, t.getLocation());
			stm.setDouble(5, t.getPrice());
			stm.setInt(6,t.getPersonId());
			stm.setInt(7, t.getActivityTypeId());
			stm.setTimestamp(8,new Timestamp(t.getDate().getTime()));
			stm.setInt(9,t.getImageId());
			stm.setString(10,""+t.getStatus());
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void update(Activity t) {
		PreparedStatement stm;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String query = "UPDATE ACTIVITY SET "
					 + " name=?, "
					 + " short_description=?, "
					 + " description=?, "
					 + " location=?, "
					 + " price=?, "
					 + " person_id=?,"
                     + " activity_type=?, "
                     + " date = ?, "
                     + " image_id = ?, "
				     + " status = ? "
				     + " WHERE ID = ?";
		try {
			stm = this.connection.prepareStatement(query);
			stm.setString(1, t.getName());
			stm.setString(2,t.getShortDescription());
			stm.setString(3, t.getDescription());
			stm.setString(4, t.getLocation());
			stm.setDouble(5,t.getPrice());
			stm.setInt(6, t.getPersonId());
			stm.setInt(7,t.getActivityType().getId());
			stm.setTimestamp(8,new Timestamp(t.getDate().getTime()));
			stm.setInt(9,t.getImageId());
			stm.setString(10,""+t.getStatus());
			stm.setInt(11,t.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}				
	}

	@Override
	public void delete(Activity t) {
		PreparedStatement stm;
		String query = "delete from activity "
					 + "where id=?";
		try {
			stm = this.connection.prepareStatement(query);
			stm.setInt(1,t.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	public List<Activity> search(ActivitySearchForm form) {

		ResultSet rs;
		String query = "SELECT id, name, short_description, description, location, price, person_id, \n" +
				"       activity_type, date,image_id,status\n" +
				"  FROM activity\n" +
				"  where name like ? and short_description like ?"+
				" and description like ?"+
				" and location like ?"+
				" and person_id = ?";

		if(form.getIniDate()!=null) {
			java.util.Date d = Date.from(form.getIniDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
			query+=" and date >= ?";
		}
		if(form.getEndDate()!=null) {
			java.util.Date d = Date.from(form.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
			query+=" and date <= ?";
		}
		PreparedStatement stm;
		List<Activity> result = new ArrayList<>();
		try {
			stm = this.connection.prepareStatement(query);
			stm.setString(1,"%"+(form.getName()==null?"":form.getName())+"%");
			stm.setString(2,"%"+(form.getShortDescription() == null?"":form.getShortDescription())+"%");
			stm.setString(3,"%"+(form.getDescription()==null?"":form.getDescription())+"%");
			stm.setString(4,"%"+(form.getLocation()==null?"":form.getLocation())+"%");
			stm.setInt(5,(form.getPersonId()));

			int ind = 6;
			if(form.getIniDate()!=null) {
				stm.setDate(ind++,  java.sql.Date.valueOf(form.getIniDate()));
			}
			if(form.getEndDate()!=null) {
				stm.setDate(ind++,  java.sql.Date.valueOf(form.getEndDate()));
			}

			rs = stm.executeQuery();
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
				"       activity_type, date,image_id,status\n" +
				"  FROM activity\n" +
				"  where "+
				" date>= ? "+
				"  and (name like ? or short_description like ?"+
				" or description like ?"+
				" or location like ?"+
				" and person_id = ?";


		query+=") order by date asc";
		PreparedStatement stm;
		List<Activity> result = new ArrayList<>();
		try {
			stm = this.connection.prepareStatement(query);
			stm.setDate(1,  new Date(new java.util.Date().getTime()));
			stm.setString(2,"%"+(form.getName()==null?"":form.getName())+"%");
			stm.setString(3,"%"+(form.getName()==null?"":form.getName())+"%");
			stm.setString(4,"%"+(form.getName()==null?"":form.getName())+"%");
			stm.setString(5,"%"+(form.getName()==null?"":form.getName())+"%");
			stm.setInt(6,(form.getPersonId()));


			rs = stm.executeQuery();
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
