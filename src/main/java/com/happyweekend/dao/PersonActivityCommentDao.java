package com.happyweekend.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.happyweekend.models.PersonActivityComment;

public class PersonActivityCommentDao implements Dao<PersonActivityComment> {
	private Connection connection;

	public PersonActivityCommentDao(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public PersonActivityComment get(Integer id) {
		PersonActivityComment activityComment = new PersonActivityComment();
		ResultSet rs;
		String query = "SELECT * "
				     + "FROM PERSON_ACTIVITY_COMMENT "
				     + "WHERE ID='" + id + "'";
		Statement stm;
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			rs.next();
			activityComment.setActivityId(rs.getInt("activity_id"));
			activityComment.setComment(rs.getString("comment"));
			activityComment.setId(rs.getInt("id"));
			activityComment.setPersonId(rs.getInt("person_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return activityComment;
	}

	@Override
	public List<PersonActivityComment> getAll() {
		List<PersonActivityComment> activityComments = new ArrayList<>();
		ResultSet rs;
		String query = "SELECT * "
				     + "FROM PERSON_ACTIVITY_COMMENT ";
		Statement stm;
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			while(rs.next()){
				PersonActivityComment activityComment = new PersonActivityComment();
				activityComment.setActivityId(rs.getInt("activity_id"));
				activityComment.setComment(rs.getString("comment"));
				activityComment.setId(rs.getInt("id"));
				activityComment.setPersonId(rs.getInt("person_id"));
				activityComments.add(activityComment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return activityComments;
	}

	@Override
	public void save(PersonActivityComment t) {
		Statement stm;
		String query = "INSERT INTO PERSON_ACTIVITY_COMMENT(id, comment, person_id, activity_id)"
					 + "values(default, '"
					 + t.getComment() + "', '"
					 + t.getPersonId() + "', '"
					 + t.getActivityId() + "')";
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(PersonActivityComment t) {
		Statement stm;
		String query = "UPDATE PERSON_ACTIVITY_COMMENT SET("
				 + "id='" + t.getId() + "', "
				 + "comment='" + t.getComment() + "', "
				 + "person_id='" + t.getPersonId() + "', "
				 + "activity_id='" + t.getActivityId() + "')";	
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void delete(PersonActivityComment t) {
		Statement stm;
		String query = "delete from PERSON_ACTIVITY_COMMENT "
					 + "where id='" + t.getId() + "'";	
		try {
			stm = this.connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public List<PersonActivityComment> getByActivity(int id) {

		List<PersonActivityComment> result = new ArrayList<>();
		ResultSet rs;
		String query = "SELECT * "
				+ "FROM PERSON_ACTIVITY_COMMENT "
				+ "WHERE activity_id ='" + id + "'";
		Statement stm;
		try {
			stm = this.connection.createStatement();
			rs = stm.executeQuery(query);
			while (rs.next()) {

                result.add(generateModel(rs));
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

    private PersonActivityComment generateModel(ResultSet rs) throws SQLException {
        PersonActivityComment activityComment = new PersonActivityComment();
        activityComment.setActivityId(rs.getInt("activity_id"));
        activityComment.setComment(rs.getString("comment"));
        activityComment.setId(rs.getInt("id"));
        activityComment.setPersonId(rs.getInt("person_id"));
        return activityComment;
    }
}
