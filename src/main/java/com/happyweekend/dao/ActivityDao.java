package com.happyweekend.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.happyweekend.models.Activity;

public class ActivityDao implements Dao<Activity>{
	private Connection connection;

	public ActivityDao(Connection connection) {
		this.connection = connection;
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
			activity.setName(rs.getString("name"));
			activity.setLocation(rs.getString("location"));
			activity.setDescription(rs.getString("description"));
			activity.setPersonId(rs.getInt("person_id"));
			activity.setShortDescription(rs.getString("short_description"));
			activity.setPrice(rs.getInt("price"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
				activity.setName(rs.getString("name"));
				activity.setLocation(rs.getString("location"));
				activity.setDescription(rs.getString("description"));
				activity.setPersonId(rs.getInt("person_id"));
				activity.setShortDescription(rs.getString("short_description"));
				activity.setPrice(rs.getInt("price"));
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
		String query = "INSERT INTO ACTIVITY(id, name, short_description, description, location, price, person_id)"
					 + "values(default, '"
					 + t.getName() + "', '"
					 + t.getShortDescription() + "', '"
					 + t.getDescription() + "', '"
					 + t.getLocation() + "', '"
					 + t.getPrice() + "', '"
					 + t.getPersonId() + "')";
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
		String query = "UPDATE ACTIVITY SET("
					 + "id='" + t.getId() + "', "
					 + "name='" + t.getName() + "', "
					 + "short_description='" + t.getShortDescription() + "', "
					 + "description='" + t.getDescription() + "', "
					 + "location='" + t.getLocation() + "', "
					 + "price='" + t.getPrice() + "', "
					 + "person_id='" + t.getPersonId() + "')";
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
