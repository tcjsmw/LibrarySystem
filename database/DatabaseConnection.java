package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * This class provides a connection between the program and the database.
 * @author ChangTan
 */
public class DatabaseConnection {
	private Connection connection = null;
	private Statement statement = null;
	
	private static final String HOST = "mysql.dur.ac.uk";
	private static final String DATABASE = "Crtjs28_...";
	private static final String USER = "rtjs28";
	private static final String PASSWORD = "swansea3";
	
	private static DatabaseConnection instance = null;
	
	 //Returns an instance to the DatabaseConnection object
	public static DatabaseConnection getInstance() {
		if(instance == null) {
			instance = new DatabaseConnection();
		}
		return instance;
	}

	/**
	 * Gets the result
	 */
	public ResultSet getResult(String query) {
		try {
			statement = connection.createStatement();
			return statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Execute a modifying instruction over the database and returns the number
	 * of rows affected by the statement.
	 * If a problem with the database occurs, returns -1.
	 */
	public int executeUpdate(String sql) {
		try {
			statement = connection.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	

	//Closes the connection with the database
	public void close()
	{
		try {
			if(connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Private methods

	private DatabaseConnection() {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Properties properties = new Properties();
			properties.setProperty("user", USER);
			properties.setProperty("password", PASSWORD);
			properties.setProperty("useSSL", "false");
			connection = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + DATABASE,properties);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
