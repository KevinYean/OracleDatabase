import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.*;


/**
 * 
 * @author Kevin YeanT
 *
 *This program should get the names of the input files as command line parameters and populate them 
 *into your database. It should be executed as "java populate yelp_business.json yelp_reviews.json yelp_checkin.json yelp_user.json
 *
 */
public class Populate {
	
    public static void main( String[] args ) throws IOException, JSONException {
        Populate populate = new Populate(args);
    }
    
    /**
     * Constructor
     * @param arg
     * @throws JSONException 
     * @throws IOException 
     */
    public Populate(String[] args) throws IOException, JSONException {
    	Connection connection = null;
    	try {
			connection = OpenConnection();
			System.out.println("Starting populate.java");
	        
	        for(String fileName: args) {
	        	System.out.println("JSON File: " + fileName);
	        	ReadFile(fileName,connection);
	        } 
	        System.out.println("Finished populating tables");
	        
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        
        System.exit( 0 ); //success
    }
    
    /**
     * This method reads any type of file and stores its content into fileString
     * @param path
     * @throws IOException
     * @throws JSONException 
     * @throws SQLException 
     */
    public void ReadFile(String path,Connection con) throws IOException, JSONException, SQLException {
		try {
			//Streaming the JSON file and saving the data into StringBuilder
			BufferedReader br = new BufferedReader(new FileReader(path));
			StringBuilder sb = new StringBuilder();
			
			String line = br.readLine();
			while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			}
			
			// Yelp User JSON File
			if (path.contains("yelp_user")) {
				String[] arr = sb.toString().split("(?=\\{\"yelping_since\")");
				Statement stmt = null;
				for (int i = 0; i < 100; i++) {
					JSONObject object = new JSONObject(arr[i]);
					
					//Prepare Statement
					String yelpID = object.getString("user_id");
					String createString = 
							"INSERT INTO YelpUser (userID) VALUES  " +
							"('"+yelpID +"')";
					
					stmt= con.createStatement(); //Creates SQL Statement
					stmt.executeUpdate(createString);
				}
			}
			else 
			
			System.out.println(sb.length());
	      
		    br.close();
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File not found: " + path.toString());
			System.out.println("Working Directory = " +
		    System.getProperty("user.dir"));
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
	
	
}
