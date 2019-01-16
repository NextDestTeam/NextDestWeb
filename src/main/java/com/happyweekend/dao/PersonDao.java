package com.happyweekend.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.happyweekend.models.Person;

public class PersonDao implements Dao<Person> {
	private final Connection connection;
	
	public PersonDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Person get(Integer id) {
		Person person = new Person();
		ResultSet rs;
		String query = "SELECT * FROM PERSON WHERE ID = ?";
		PreparedStatement stm;
		try {
			stm = this.connection.prepareStatement(query);
			stm.setInt(1,id);
			rs = stm.executeQuery();
			rs.next();
			person.setAge(rs.getDate("age"));
			person.setEmail(rs.getString("email"));
			person.setFirstName(rs.getString("first_name"));
			person.setId(rs.getInt("id"));
			person.setLastName(rs.getString("last_name"));
			person.setPersonTypeId(rs.getInt("person_type_id"));
			person.setImageId(rs.getInt("image_id"));
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return person;
	}

	@Override
	public List<Person> getAll() {
		List<Person> personList = new ArrayList<>();
		ResultSet rs;
		Statement stm;
		String query = "SELECT * FROM PERSON";
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			while(rs.next()) {
				Person person = new Person();
				person.setAge(rs.getDate("age"));
				person.setEmail(rs.getString("email"));
				person.setFirstName(rs.getString("first_name"));
				person.setId(rs.getInt("id"));
				person.setLastName(rs.getString("last_name"));
				person.setPersonTypeId(rs.getInt("person_type_id"));
				personList.add(person);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return personList;
	}

	@Override
	public void save(Person t) {
		PreparedStatement stm;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = String.format("%s",t.getAge()==null?"null":
				" TIMESTAMP '"+sdf.format(t.getAge())+"'");

		String query = "INSERT INTO PERSON(id, first_name, last_name, email, age, person_type_id, IMAGE_ID)"
					 + "values(default, ?,?,?,?,?,?)";
		try {
			stm = this.connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			stm.setString(1,t.getFirstName());
			stm.setString(2, t.getLastName());
			stm.setString(3, t.getEmail());
			if(t.getAge()!=null)
				stm.setDate(4, new java.sql.Date(t.getAge().getTime()));
			else
				stm.setDate(4, null);
			stm.setInt(5, t.getPersonTypeId());
			stm.setObject(6, t.getImageId());
			stm.executeUpdate();
			if(stm.getGeneratedKeys().next()) {
				t.setId(stm.getGeneratedKeys().getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Person t) {
		PreparedStatement stm;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String query = "UPDATE PERSON SET "
				 + "first_name=?, "
				 + "last_name=?, "
				 + "email=?, "
				 + "age=?, "
				 + "person_type_id=?, "
				+ " image_id=?"
				+ " WHERE id = ?";
		try {
			stm = this.connection.prepareStatement(query);
				stm.setString(1,t.getFirstName());
				stm.setString(2, t.getLastName());
				stm.setString(3, t.getEmail());
				if(t.getAge()!=null)
					stm.setDate(4, new java.sql.Date(t.getAge().getTime()));
				else
					stm.setDate(4,null);
				stm.setInt(5,t.getPersonTypeId());
				stm.setInt(6,t.getImageId());
				stm.setInt(7, t.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void delete(Person t) {
		PreparedStatement stm;
		String query = "delete from person "
					 + "where id=?";
		try {
			stm = this.connection.prepareStatement(query);
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
}
