package com.happyweekend.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.happyweekend.models.ActivityType;

public class ActivityTypeDao implements Dao<ActivityType> {
	Connection connection;
	
	public ActivityTypeDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public ActivityType get(Integer id) {
		ActivityType activityType = new ActivityType();
		ResultSet rs;
		String query = "SELECT * FROM ACTIVITY_TYPE WHERE ID=?";
		PreparedStatement stm;
		try {
			stm = this.connection.prepareStatement(query);
			stm.setInt(1,id);
			rs = stm.executeQuery();
			rs.next();
			activityType.setId(rs.getInt("id"));
			activityType.setName(rs.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return activityType;
	}

	@Override
	public List<ActivityType> getAll() {
		List<ActivityType> activityTypeList = new ArrayList<>();
		ResultSet rs;
		String query = "SELECT * FROM ACTIVITY_TYPE";
		Statement stm;
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			while(rs.next()) {		
				ActivityType activityType = new ActivityType();
				activityType.setId(rs.getInt("id"));
				activityType.setName(rs.getString("name"));
				activityTypeList.add(activityType);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return activityTypeList;
	}

	@Override
	public void save(ActivityType t) {
		PreparedStatement stm;
		String query = "INSERT INTO ACTIVITY_TYPE(id, name)"
					 + "values(default, ?)";
		try {
			stm = this.connection.prepareStatement(query);
			stm.setString(1,t.getName());
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void update(ActivityType t) {
		/*Statement stm;
		String query = "UPDATE ACTIVITY_TYPE SET("
					 + "id='" + t.getId() + "', "
					 + "name='" + t.getName() + "')";
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}

	@Override
	public void delete(ActivityType t) {
		PreparedStatement stm;
		String query = "delete from activity_type "
					 + "where id=?";
		try {
			stm = this.connection.prepareStatement(query);
			stm.setInt(1,t.getId());
			stm.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}		
	}
}
