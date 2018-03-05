import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * 
 * @author Kevin Yean
 *This the Start function which will be the class that drives the whole program.
 */
public class Start {

	public static void main(String[] args) {
		Start start = new Start();
	}

	/**
	 * Constructor which immediately begins the program as soon as it runs.
	 */
	public Start() {
		Connection connection = null;
		try { 
			connection = OpenConnection(); 
			System.out.println("Succesfully Connected to Database.");
			//Create Tables
			CreateTables(connection);
			//Drop Tables
			//DropTables(connection);
		} catch (SQLException e) { 
			System.err.println("Errors occurs when communicating with the database server: " + e.getMessage()); 
		} catch (ClassNotFoundException e) { 
			System.err.println("Cannot find the database driver"); 
		} finally { 
			// Never forget to close database connection 
			CloseConnection(connection); 
			System.out.println("Succesfully Disconnected to Database.");
		} 
	} 

	/**
	 * Method attempts to connect to the database given the input provided by the user.
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private Connection OpenConnection() throws SQLException, ClassNotFoundException{
		
		// Load the Oracle database driver \
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); 
		
		String host = "localhost"; 
		String port = "1521"; 
		String dbName = "kevin"; 
		String userName = "Scott"; 
		String password = "Tiger123";
		
//		//Scanner input
//		Scanner reader = new Scanner(System.in);
//		System.out.print("Enter the host name: ");
//		host = reader.next();
//		System.out.print("Enter the port name: ");
//		port = reader.next();
//		System.out.print("Enter the dbName name: ");
//		dbName = reader.next();
//		System.out.print("Enter the userName name: ");
//		userName = reader.next();
//		System.out.print("Enter the password name: ");
//		password = reader.next();
//		reader.close();
		
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

	/**
	 * Method creates the SQL Tables.
	 * @param con
	 * @throws SQLException
	 */
	private void CreateTables(Connection con) throws SQLException {
		//Class used for reading a file.
		Reader reader = new Reader();
		//Reads from createdb.sql file and bring it back as a string.
		
		try {
			reader.ReadFile("src\\Data\\createdb.sql");
			//System.out.println(reader.GetFileString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String createString = reader.GetFileString();
		String queries[] = createString.split(";");

		Statement stmt = null;
		try {
			for(String temp : queries) {
				stmt = con.createStatement();
				stmt.executeUpdate(temp);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) { stmt.close(); }
			System.out.println("Succesfully Added All Tables");
		}
		
//		String createString =
//				"create Table ss " +
//				"(COF_NAME varchar(32) NOT NULL, " +
//				"SUP_ID int NOT NULL, " +
//				"PRICE float NOT NULL, " +
//				"SALES integer NOT NULL, " +
//				"TOTAL integer NOT NULL, " +
//				"PRIMARY KEY (COF_NAME)) " ;

		//Statement stmt = null;
//		try {
//			stmt = con.createStatement();
//			stmt.executeUpdate(createString);
//		} catch (SQLException e) {
//			System.out.println(e);
//		} finally {
//			if (stmt != null) { stmt.close(); }
//			System.out.println("Succesfully Added All Tables");
//		}
	}
	
	private void DropTables(Connection con) throws SQLException {
		//Class used for reading a file.
		Reader reader = new Reader();
		try {
			reader.ReadFile("src\\Data\\dropdb.sql");
			//System.out.println(reader.GetFileString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String createString = reader.GetFileString();
		String queries[] = createString.split(";");

		Statement stmt = null;
		try {
			for(String temp : queries) {
				stmt = con.createStatement();
				stmt.executeUpdate(temp);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) { stmt.close(); }
			System.out.println("Succesfully Dropped All Tables");
		}
	}
}
