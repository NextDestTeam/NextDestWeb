package com.happyweekend.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.happyweekend.models.PersonType;

public class PersonTypeDao implements Dao<PersonType> {
	Connection connection;
	
	public PersonTypeDao(Connection connection) {
		// TODO Auto-generated constructor stub
		this.connection = connection;
	}

	@Override
	public PersonType get(Integer id) {
		// TODO Auto-generated method stub
		PersonType personType = new PersonType();
		ResultSet rs;
		String query = "SELECT * FROM PERSON_TYPE WHERE ID='" + id + "'";
		Statement stm;
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			rs.next();
			personType.setId(rs.getInt("id"));;
			personType.setName(rs.getString("name"));;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return personType;
	}

	@Override
	public List<PersonType> getAll() {
		// TODO Auto-generated method stub
		List<PersonType> personTypeList = new ArrayList<>();
		ResultSet rs;
		String query = "SELECT * FROM PERSON_TYPE";
		Statement stm;
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			while(rs.next()) {		
				PersonType personType = new PersonType();
				personType.setId(rs.getInt("id"));;
				personType.setName(rs.getString("name"));
				personTypeList.add(personType);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return personTypeList;
	}

	@Override
	public void save(PersonType t) {
		// TODO Auto-generated method stub
		Statement stm;
		String query = "INSERT INTO PERSON_TYPE(id, name)"
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
	public void update(PersonType t) {
		// TODO Auto-generated method stub
		Statement stm;
		String query = "UPDATE PERSON_TYPE SET("
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
	public void delete(PersonType t) {
		// TODO Auto-generated method stub
		Statement stm;
		String query = "delete from person_type "
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
