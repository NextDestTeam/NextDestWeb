package connectionManager;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConnectionManager {
	private Connection connection;
	private String strStatus;
	
	public ConnectionManager() {
		 
	}
	
	public Connection connect() {
		try {
			InitialContext cxt = new InitialContext(); 
			if ( cxt != null )
			{
			    DataSource ds = (DataSource) cxt.lookup( "java:jboss/PostgresXA");
				if ( ds == null ) {
					this.strStatus = "Error with the creation of the datasource";
				}else{
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

}
