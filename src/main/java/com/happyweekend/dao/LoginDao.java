package com.happyweekend.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.happyweekend.models.Login;

public class LoginDao implements Dao<Login> {
	Connection connection;

	public LoginDao(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public Login get(Integer id) {
		Login login = new Login();
		ResultSet rs;
		String query = "SELECT * FROM LOGIN WHERE ID='" + id + "'";
		Statement stm;
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			rs.next();
			login.setId(rs.getInt("id"));
			login.setLoginName(rs.getString("login_name"));
			login.setPassword(rs.getString("password"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return login;
	}

	@Override
	public List<Login> getAll() {
		List<Login> logins = new ArrayList<>();
		ResultSet rs;
		String query = "SELECT * FROM LOGIN ";
		Statement stm;
		try {
			Login login = new Login();
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			rs.next();
			login.setId(rs.getInt("id"));
			login.setLoginName(rs.getString("login_name"));
			login.setPassword(rs.getString("password"));
			logins.add(login);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return logins;
	}

	@Override
	public void save(Login t) {
		Statement stm;
		String query = "INSERT INTO login(id, login_name, password)"
					 + "values(default, '"
					 + t.getLoginName() + "', '"
					 + t.getPassword() + "')";
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void update(Login t) {
		Statement stm;
		String query = "UPDATE LOGIN SET("
					 + "id='" + t.getId() + "', "
					 + "login_name='" + t.getLoginName() + "', "
					 + "password='" + t.getPassword() + "')";
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Login t) {
		Statement stm;
		String query = "REMOVE FROM LOGIN "
				     + "WHERE id='" + t.getId() + "'";
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
