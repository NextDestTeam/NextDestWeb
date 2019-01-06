package com.happyweekend.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		String query = "SELECT * FROM PERSON WHERE ID='" + id + "'";
		Statement stm;
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
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
		Statement stm;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = String.format("%s",t.getAge()==null?"null":
				" TIMESTAMP '"+sdf.format(t.getAge())+"'");

		String query = "INSERT INTO PERSON(id, first_name, last_name, email, age, person_type_id, IMAGE_ID)"
					 + "values(default, '"
					 + t.getFirstName() + "', '"
					 + t.getLastName() + "', '"
					 + t.getEmail() + "', "
					 + date+ ", '"
					 + t.getPersonTypeId() +"', "
					+ (t.getImageId()==null?null:+t.getImageId())+")";
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
			if(stm.getGeneratedKeys().next()) {
				t.setId(stm.getGeneratedKeys().getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Person t) {
		Statement stm;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String query = "UPDATE PERSON SET "
				 + "first_name='" + t.getFirstName() + "', "
				 + "last_name='" + t.getLastName() + "', "
				 + "email='" + t.getEmail() + "', "
				 + "age='" + sdf.format(t.getAge()) + "', "
				 + "person_type_id='" + t.getPersonTypeId() +"', "
				+ " image_id="+t.getImageId()
				+ " WHERE id = "+ t.getId();
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void delete(Person t) {
		Statement stm;
		String query = "delete from person "
					 + "where id='" + t.getId() + "'";	
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
}
