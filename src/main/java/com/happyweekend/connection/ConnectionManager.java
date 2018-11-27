package com.happyweekend.connection;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConnectionManager {
	private Connection connection;
	private String strStatus;
	private static ConnectionManager instance;
	
	private ConnectionManager() {
		 
	}
	
	public Connection connect() {
		try {
			InitialContext cxt = new InitialContext();
			if ( cxt != null )
			{

				Context ctx = new InitialContext();

				DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PostgresXA");;


				if (ds == null) {
					this.strStatus = "Error with the creation of the datasource";
				} else {

					this.connection = ds.getConnection();
				}

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return this.connection;
	}
	
	public void close() {
		try
		{
			this.connection.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static ConnectionManager getInstance(){
		return (instance=(instance!=null?instance:new ConnectionManager()));
	}



}
