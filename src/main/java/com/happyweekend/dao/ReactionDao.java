package com.happyweekend.dao;

import java.sql.*;
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
		String query = "SELECT * FROM REACTION WHERE ID=?";
		PreparedStatement stm;
		try {
			stm = this.connection.prepareStatement(query);
			stm.setInt(1,id);
			rs = stm.executeQuery();
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


	public List<Reaction> getAllByActivity(int idActivity) {
		List<Reaction> reactions = new ArrayList<>();
		ResultSet rs;
		String query = "SELECT * FROM REACTION WHERE activity = ?";
		PreparedStatement stm;
		try {
			stm = this.connection.prepareStatement(query);
			stm.setInt(1,idActivity);
			rs = stm.executeQuery();
			while(rs.next()){
				Reaction reaction = new Reaction();
				reaction.setActivity(rs.getInt("activity"));
				reaction.setId(rs.getInt("id"));
				reaction.setPersonId(rs.getInt("person_id"));
				reaction.setReaction(rs.getString("reaction"));
				reactions.add(reaction);
			}
			return reactions;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
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
			return reactions;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public void save(Reaction t) {
		PreparedStatement stm;
		String query = "INSERT INTO REACTION(id, reaction, person_id, activity)"
					 + "values(default, ?,?,?)";
		try {
			stm = this.connection.prepareStatement(query);
			stm.setString(1,t.getReaction());
			stm.setInt(2,t.getPersonId());
			stm.setInt(3,t.getActivity());
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void update(Reaction t) {
		/*PreparedStatement stm;
		String query = "UPDATE REACTION SET("
					 + "id='" + t.getId() + "', "
					 + "reaction='" + t.getReaction()+ "', "
					 + "person_id='" + t.getPersonId() + "', "
					 + "activity='" + t.getActivity() + "')";
		try {
			stm = this.connection.prepareStatement(query);
			stm.setInt(1,t.getId());
			stm.setString(2,t.getReaction());
			stm.setInt(3,t.getPersonId());
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}

	@Override
	public void delete(Reaction t) {
		PreparedStatement stm;
		String query = "DELETE FROM REACTION "
					 + "WHERE id=?";
		try {
			stm = this.connection.prepareStatement(query);
			stm.setInt(1,t.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}


}
