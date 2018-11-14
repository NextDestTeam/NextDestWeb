package com.happyweekend.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.happyweekend.models.Reaction;

public class ReactionDao implements Dao<Reaction> {
	Connection connection;
	
	public ReactionDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Reaction get(Integer id) {
		Reaction reaction = new Reaction();
		ResultSet rs;
		String query = "SELECT * FROM REACTION WHERE ID='" + id + "'";
		Statement stm;
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			rs.next();
			reaction.setActivity(rs.getInt("activity"));
			reaction.setId(rs.getInt("id"));
			reaction.setPersonId(rs.getInt("person_id"));
			reaction.setReaction(rs.getString("reaction"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reaction;
	}

	@Override
	public List<Reaction> getAll() {
		List<Reaction> reactions = new ArrayList<>();
		ResultSet rs;
		String query = "SELECT * FROM REACTION";
		Statement stm;
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			while(rs.next()){
				Reaction reaction = new Reaction();
				reaction.setActivity(rs.getInt("activity"));
				reaction.setId(rs.getInt("id"));
				reaction.setPersonId(rs.getInt("person_id"));
				reaction.setReaction(rs.getString("reaction"));
				reactions.add(reaction);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(Reaction t) {
		Statement stm;
		String query = "INSERT INTO REACTION(id, reaction, person_id, activity)"
					 + "values(default, '"
					 + t.getReaction() + "', '"
					 + t.getPersonId() + "', '"
					 + t.getActivity() +"')";
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void update(Reaction t) {
		Statement stm;
		String query = "UPDATE REACTION SET("
					 + "id='" + t.getId() + "', "
					 + "reaction='" + t.getReaction()+ "', "
					 + "person_id='" + t.getPersonId() + "', "
					 + "activity='" + t.getActivity() + "')";
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}				
	}

	@Override
	public void delete(Reaction t) {
		Statement stm;
		String query = "DELETE FROM REACTION "
					 + "WHERE id='" + t.getId() + "'";	
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

}
