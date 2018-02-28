import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
	}

	public Main() {
		Run();
	}

	public void Run() {
		Connection con = null;
		try { 
			con = OpenConnection(); 
			createTable(con);
			System.out.println("Hello");
		} catch (SQLException e) { 
			System.err.println("Errors occurs when communicating with the database server: " + e.getMessage()); 
		} catch (ClassNotFoundException e) { 
			System.err.println("Cannot find the database driver"); 
		} finally { 
			// Never forget to close database connection 
			CloseConnection(con); 
		} 
	} 


	private Connection OpenConnection() throws SQLException, ClassNotFoundException{
		// Load the Oracle database driver \

		DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); 
		/* 
		Here is the information needed when connecting to a database 
		server. These values are now hard-coded in the program. In 
		general, they should be stored in some configuration file and 
		read at run time. 
		 */ 

		String host = "localhost"; 
		String port = "1521"; 
		String dbName = "kevin"; 
		String userName = "Scott"; 
		String password = "Tiger123";
		// Construct the JDBC URL 
		String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName; 
		return DriverManager.getConnection(dbURL, userName, password); 
	}

	/** 
	 * Close the database connection 
	 * @param con 
	 */ 
	private void CloseConnection(Connection con) { 

		try { 
			con.close(); 
		} catch (SQLException e) { 
			System.err.println("Cannot close connection: " + e.getMessage()); 
		} 
	}

	public void createTable(Connection con) throws SQLException {
		String createString =
				"create Table s " +
				"(COF_NAME varchar(32) NOT NULL, " +
				"SUP_ID int NOT NULL, " +
				"PRICE float NOT NULL, " +
				"SALES integer NOT NULL, " +
				"TOTAL integer NOT NULL, " +
				"PRIMARY KEY (COF_NAME)) " ;

		Statement stmt = null;
		System.out.println("Hello");
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(createString);
		} catch (SQLException e) {
			System.out.println("Hell");
			//JDBCTutorialUtilities.printSQLException(e);
		} finally {
			if (stmt != null) { stmt.close(); }
		}
	}
}
