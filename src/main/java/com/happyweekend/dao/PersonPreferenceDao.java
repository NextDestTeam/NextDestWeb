package com.happyweekend.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.happyweekend.models.PersonPreference;

public class PersonPreferenceDao implements Dao<PersonPreference> {
	Connection connection;
	
	public PersonPreferenceDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public PersonPreference get(Integer id) {
		PersonPreference personPreference = new PersonPreference();
		ResultSet rs;
		String query = "SELECT * FROM PERSON_PREFERENCE WHERE ID='" + id + "'";
		Statement stm;
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			rs.next();
			personPreference.setActivityTypeId(rs.getInt("activity_type_id"));
			personPreference.setId(rs.getInt("id"));
			personPreference.setPersonId(rs.getInt("person_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return personPreference;
	}

	@Override
	public List<PersonPreference> getAll() {
		List<PersonPreference> personPreferences = new ArrayList<>();
		ResultSet rs;
		String query = "SELECT * FROM PERSON_PREFERENCE";
		Statement stm;
		try {		
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			while(rs.next()) {
				PersonPreference personPreference = new PersonPreference();
				personPreference.setActivityTypeId(rs.getInt("activity_type_id"));
				personPreference.setId(rs.getInt("id"));
				personPreference.setPersonId(rs.getInt("person_id"));
				personPreferences.add(personPreference);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(PersonPreference t) {
		Statement stm;
		String query = "INSERT INTO PERSON_PREFERENCE(id, person_id, activity_type_id)"
					 + "values(default, '"
					 + t.getPersonId() + "','"
					 + t.getActivityTypeId() + "')";
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void update(PersonPreference t) {
		Statement stm;
		String query = "UPDATE ACTIVITY_TYPE SET("
					 + "id='" + t.getId() + "', "
					 + "person_id='" + t.getPersonId() + "', "
					 + "activity_type_id='" + t.getActivityTypeId() + "')";
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void delete(PersonPreference t) {
		Statement stm;
		String query = "delete from activity_type "
					 + "where id='" + t.getId() + "'";	
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

}
