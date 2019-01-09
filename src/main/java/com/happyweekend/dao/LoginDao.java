package com.happyweekend.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.happyweekend.models.Login;
import sun.rmi.log.LogInputStream;

public class LoginDao implements Dao<Login> {
	Connection connection;

	public LoginDao(Connection connection) {
		this.connection = connection;
	}


	public Login get(Login login) {
		ResultSet rs;
		String query = "SELECT * FROM LOGIN WHERE login_name LIKE ? AND PASSWORD LIKE ?";
		PreparedStatement stm;
		try {
			stm = this.connection.prepareStatement(query);
			stm.setString(1,login.getLoginName());
			stm.setString(2,login.getPassword());
			rs = stm.executeQuery();
			while(rs.next()) {
				login.setId(rs.getInt("id"));
				login.setLoginName(rs.getString("login_name"));
				login.setPassword(rs.getString("password"));
				login.setPersonId(rs.getInt("person_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return login;
	}
	
	@Override
	public Login get(Integer id) {
		Login login = new Login();
		ResultSet rs;
		String query = "SELECT * FROM LOGIN WHERE ID = ?";
		PreparedStatement stm;
		try {
			stm = this.connection.prepareStatement(query);
			stm.setInt(1,id);
			rs = stm.executeQuery();
			rs.next();
			login.setId(rs.getInt("id"));
			login.setLoginName(rs.getString("login_name"));
			login.setPassword(rs.getString("password"));
			login.setPersonId(rs.getInt("person_id"));
			
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
			login.setPersonId(rs.getInt("person_id"));
			logins.add(login);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return logins;
	}

	@Override
	public void save(Login t) {
		PreparedStatement stm;
		String query = "INSERT INTO login(id,person_id, login_name, password)"
					 + "values(default,?,?,?)";
		try {
			stm = this.connection.prepareStatement(query);
			stm.setInt(1,t.getPersonId());
			stm.setString(2, t.getLoginName());
			stm.setString(3,t.getPassword());
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void update(Login t) {
		PreparedStatement stm;
		String query = "UPDATE LOGIN SET "
					 + "login_name=?, "
					 + "password=?"+
				" WHERE id = "+t.getId();
		try {
			stm = this.connection.prepareStatement(query);
			stm.setString(1,t.getLoginName());
			stm.setString(2,t.getPassword());
			stm.executeUpdate();
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

	public Login loadByUsername(String username) {
		Login login = null;
		ResultSet rs;
		String query = "SELECT * FROM LOGIN WHERE login_name='" + username + "'";
		Statement stm;
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);

			if(rs.next()) {
				login = new Login();
				login.setId(rs.getInt("id"));
				login.setLoginName(rs.getString("login_name"));
				login.setPassword(rs.getString("password"));
				login.setPersonId(rs.getInt("person_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return login;
	}
}
