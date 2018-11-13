package com.happyweekend.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.happyweekend.models.ActivityType;

public class ActivityTypeDao implements Dao<ActivityType> {
	Connection connection;
	
	public ActivityTypeDao(Connection connection) {
		// TODO Auto-generated constructor stub
		this.connection = connection;
	}

	@Override
	public ActivityType get(Integer id) {
		// TODO Auto-generated method stub
		ActivityType activityType = new ActivityType();
		ResultSet rs;
		String query = "SELECT * FROM ACTIVITY_TYPE WHERE ID='" + id + "'";
		Statement stm;
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			rs.next();
			activityType.setId(rs.getInt("id"));;
			activityType.setName(rs.getString("name"));;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activityType;
	}

	@Override
	public List<ActivityType> getAll() {
		// TODO Auto-generated method stub
		List<ActivityType> activityTypeList = new ArrayList<>();
		ResultSet rs;
		String query = "SELECT * FROM ACTIVITY_TYPE";
		Statement stm;
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			while(rs.next()) {		
				ActivityType activityType = new ActivityType();
				activityType.setId(rs.getInt("id"));;
				activityType.setName(rs.getString("name"));
				activityTypeList.add(activityType);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activityTypeList;
	}

	@Override
	public void save(ActivityType t) {
		// TODO Auto-generated method stub
		Statement stm;
		String query = "INSERT INTO ACTIVITY_TYPE(id, name)"
					 + "values((select max(id) from activity_type), '"
					 + t.getName() + "')";
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void update(ActivityType t) {
		// TODO Auto-generated method stub
		Statement stm;
		String query = "UPDATE ACTIVITY_TYPE SET("
					 + "id='" + t.getId() + "', "
					 + "name='" + t.getName() + "')";
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}

	@Override
	public void delete(ActivityType t) {
		// TODO Auto-generated method stub
		Statement stm;
		String query = "delete from activity_type "
					 + "where id='" + t.getId() + "'";	
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
