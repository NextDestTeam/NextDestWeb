package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Person;

public class PersonDao implements Dao<Person> {
	private Connection connection;
	
	public PersonDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Person get(Integer id) {
		// TODO Auto-generated method stub
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return person;
	}

	@Override
	public List<Person> getAll() {
		// TODO Auto-generated method stub
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return personList;
	}

	@Override
	public void save(Person t) {
		// TODO Auto-generated method stub
		Statement stm;
		String query = "INSERT INTO PERSON(first_name, last_name, email, age, person_type_id)"
					 + "values((select max(id) from person), '"
					 + t.getFirstName() + "', '"
					 + t.getLastName() + "', '"
					 + t.getEmail() + "', '"
					 + t.getAge().toString() + "', '"
					 + t.getPersonTypeId() + "')";
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Person t) {
		// TODO Auto-generated method stub
		Statement stm;
		String query = "UPDATE PERSON SET("
				 + "id='" + t.getId() + "', "
				 + "first_name='" + t.getFirstName() + "', "
				 + "last_name='" + t.getLastName() + "', "
				 + "email='" + t.getEmail() + "', "
				 + "age='" + t.getAge().toString() + "', "
				 + "person_type_id='" + t.getPersonTypeId() + "')";	
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void delete(Person t) {
		// TODO Auto-generated method stub
		Statement stm;
		String query = "delete from person "
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
