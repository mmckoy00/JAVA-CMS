package dbconnection;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class Db {
	
	private BasicDataSource ds =  new BasicDataSource();
	
	{
		ds.setUrl("jdbc:mysql://localhost:3306/demo"); 
		ds.setUsername("root");
		ds.setPassword("@7235Ma@@");
		ds.setMaxOpenPreparedStatements(20);
		ds.setMaxTotal(3);
		ds.setMinIdle(1);
		ds.setMaxIdle(2);
	}
	
	
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
	public void closeConnection() throws SQLException {
		ds.close();
	}
	
}
