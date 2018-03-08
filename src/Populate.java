import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.json.*;

import javafx.scene.input.DataFormat;

/**
 * 
 * @author Kevin YeanT
 *
 *         This program should get the names of the input files as command line
 *         parameters and populate them into your database. It should be
 *         executed as "java populate yelp_business.json yelp_reviews.json
 *         yelp_checkin.json yelp_user.json
 *
 */
public class Populate {

	Connection connection;

	public static void main(String[] args) throws IOException, JSONException, ParseException {
		Populate populate = new Populate(args);
	}

	/**
	 * Constructor
	 * 
	 * @param arg
	 * @throws JSONException
	 * @throws IOException
	 * @throws ParseException
	 */
	public Populate(String[] args) throws IOException, JSONException, ParseException {
		connection = null;
		try {
			connection = OpenConnection();
			System.out.println("Starting populate.java");

			for (String fileName : args) {
				System.out.println("\nJSON File: " + fileName);
				DropTableData(fileName);
				ReadFile(fileName);
			}
			System.out.println("Finished populating tables");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.exit(0); // success
	}

	/**
	 * Methods drop the values in the table of filename.
	 * 
	 * @param fileName
	 * @throws SQLException
	 */
	public void DropTableData(String path) throws SQLException {

		Statement stmt = null;
		String createString = "";

		// Drop Values in depending on which File is called.
		if (path.contains("yelp_user")) {
			long start = System.currentTimeMillis();
			System.out.println("Dropping all values in Reviews");
			// Prepare Statement to DROP ALL VALUES From Yelp User.
			createString = "DELETE FROM Reviews";
			stmt = connection.createStatement(); // Creates SQL Statement
			stmt.executeUpdate(createString);
			long end = System.currentTimeMillis();
			System.out.println("Deletion Took : " + ((end - start) / 1000));

			System.out.println("Dropping all values in YelpUser");
			start = System.currentTimeMillis();
			// Prepare Statement to DROP ALL VALUES From Yelp User.
			createString = "DELETE FROM YelpUser";
			stmt = connection.createStatement(); // Creates SQL Statement
			stmt.executeUpdate(createString);
			end = System.currentTimeMillis();
			System.out.println("Deletion Took : " + ((end - start) / 1000));

		} else if (path.contains("review")) {
			long start = System.currentTimeMillis();
			System.out.println("Dropping all values in Reviews");
			// Prepare Statement to DROP ALL VALUES From Yelp User.
			createString = "DELETE FROM Reviews";
			stmt = connection.createStatement(); // Creates SQL Statement
			stmt.executeUpdate(createString);
			long end = System.currentTimeMillis();
			System.out.println("Deletion Took : " + ((end - start) / 1000));

		}

		else if (path.contains("business")) {
			System.out.println("Dropping all values in Business");
			// Prepare Statement to DROP ALL VALUES From Yelp User.
			createString = "DELETE FROM Business";
			stmt = connection.createStatement(); // Creates SQL Statement
			stmt.executeUpdate(createString);

			System.out.println("Dropping all values in BusinessWithCategory");
			// Prepare Statement to DROP ALL VALUES From Yelp User.
			createString = "DELETE FROM BusinessWithCategory";
			stmt = connection.createStatement(); // Creates SQL Statement
			stmt.executeUpdate(createString);
		}

		else if (path.contains("checkin")) {
			System.out.println("Dropping all values in Checkin");
			// Prepare Statement to DROP ALL VALUES From Yelp User.
			createString = "DELETE FROM Checkin";
			stmt = connection.createStatement(); // Creates SQL Statement
			stmt.executeUpdate(createString);
		}
	}

	/**
	 * This method reads any type of file and stores its content into fileString
	 * 
	 * @param path
	 * @throws IOException
	 * @throws JSONException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void ReadFile(String path) throws IOException, JSONException, SQLException, ParseException {
		try {
			// Streaming the JSON file and saving the data into StringBuilder
			BufferedReader br = new BufferedReader(new FileReader(path));
			StringBuilder sb = new StringBuilder();

			PreparedStatement ps = null;

			if (path.contains("review")) {
				ps = connection.prepareStatement(
						"Insert INTO " + "Reviews(reviewsID,businessID,userID,stars, voteCount, reviewDate) "
								+ "VALUES (?,?,?,?,?,?)");
			}

			String line = "";
			long start = System.currentTimeMillis();

			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());

				// Reviews JSon File
				if (path.contains("review")) {
					JSONObject object = new JSONObject(line);
					String reviewID = object.getString("review_id");
					String businessID = object.getString("business_id");
					String yelpID = object.getString("user_id");
					float stars = (float) object.getLong("stars");

					JSONObject voteObject = object.getJSONObject("votes");
					int sumVotes = 0;
					sumVotes += (voteObject.getInt("funny"));
					sumVotes += (voteObject.getInt("useful"));
					sumVotes += (voteObject.getInt("cool"));

					String yelpDate = object.getString("date");
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-DD");
					java.util.Date javaDate = df.parse(yelpDate);
					java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());

					ps.setString(1, reviewID);
					ps.setString(2, businessID);
					ps.setString(3, yelpID);
					ps.setFloat(4, stars);
					ps.setFloat(5, sumVotes);
					ps.setDate(6, sqlDate);
					ps.addBatch();
					ps.clearParameters();
				}
			}

			if (ps != null) {
				ps.executeBatch();
			}

			// Yelp User JSON File
			if (path.contains("yelp_user")) {
				System.out.println("Adding all values in YelpUser");
				String[] arr = sb.toString().split("(?=\\{\"yelping_since\")");
				ps = connection.prepareStatement(
						"Insert INTO " + "YelpUser(userID,name,yelpingSince,reviewsCount,averageStars,friendCount) "
								+ "VALUES (?,?,?,?,?,?)");
				for (int i = 0; i < arr.length; i++) {
					// Batch Attempt
					JSONObject object = new JSONObject(arr[i]);
					String yelpID = object.getString("user_id");
					String yelpName = object.getString("name");

					String yelpData = object.getString("yelping_since");
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
					java.util.Date javaDate = df.parse(yelpData);
					java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());

					int yelpReviewCount = object.getInt("review_count");
					float averageStars = (float) object.getDouble("average_stars");

					JSONArray friendArray = object.getJSONArray("friends");
					int friendCount = friendArray.length();

					ps.setString(1, yelpID);
					ps.setString(2, yelpName);
					ps.setDate(3, sqlDate);
					ps.setInt(4, yelpReviewCount);
					ps.setFloat(5, averageStars);
					ps.setInt(6, friendCount);
					ps.addBatch();
					ps.clearParameters();
				}
				ps.executeBatch();
			}

			// Business User JSON File
			if (path.contains("business")) {

				// Go through every businesses

				System.out.println("Adding all values in Business");
				String[] arr = sb.toString().split("(?=\\{\"business_id\")");
				ps = connection.prepareStatement(
						"Insert INTO " + "Business(businessID,city,state,name,stars) " + "VALUES (?,?,?,?,?)");

				// Find all the Maincategories add.
				ArrayList<MainCategory> categories = new ArrayList<MainCategory>();

				{
					String sqlQuery = "SELECT * " + "FROM BusinessCategory";

					// Execute Select SQL Statement
					Statement stmt = connection.createStatement();
					System.out.println(sqlQuery);
					ResultSet r = stmt.executeQuery(sqlQuery); // Inserts the query into ShowQuery
					ResultSetMetaData meta = r.getMetaData();
					int i = 0;
					while (r.next()) {
						// System.out.println("Categories: " + r.getString("categoryName"));
						MainCategory temp = new MainCategory(r.getString("categoryName"));
						categories.add(temp);
						i++;
					}
					stmt.close();
				}

				for (int i = 0; i < arr.length; i++) {
					// Batch Attempt
					JSONObject object = new JSONObject(arr[i]);
					String businessID = object.getString("business_id");
					String city = object.getString("city");
					String state = object.getString("state");
					String name = object.getString("name");
					float stars = (float) object.getDouble("stars");

					// Handling subcategories
					JSONArray arrCat = object.getJSONArray("categories");
					ArrayList<String> temp = new ArrayList<String>();

					{
						for (int x = 0; x < arrCat.length(); x++) {
							temp.add(arrCat.getString(x));
						}

						for (int t = 0; t < categories.size(); t++) {
							if (temp.contains(categories.get(t).mainCategory.toString())) {
								categories.get(t).subCategory.addAll(temp);

							}
						}
					}
					
					//Have a second prepared statement to handle BusinessWithCategory

					ps.setString(1, businessID);
					ps.setString(2, city);
					ps.setString(3, state);
					ps.setString(4, name);
					ps.setFloat(5, stars);
					ps.addBatch();
					ps.clearParameters();
				}
				ps.executeBatch();
				
				//Remove Main Categories from each hashes
				for(int i = 0 ; i < categories.size() ; i++) {
					for(int x = 0; x < categories.get(i).subCategory.size();x++) {
						for(int z = 0 ; z < categories.size() ; z++) {
							categories.get(i).subCategory.remove(categories.get(z).mainCategory);
						}
					}					
				}
				

				for (int i = 0; i < categories.size(); i++) {
					System.out.println(categories.get(i).mainCategory);
					System.out.println(categories.get(i).subCategory.toString());
					System.out.println("-----");
				}
				
				//Adding subcategories to tables
			}

			long end = System.currentTimeMillis();
			System.out.println("Adding Values Took : " + ((end - start) / 1000));

			br.close();

		} catch (

		FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File not found: " + path.toString());
			System.out.println("Working Directory = " + System.getProperty("user.dir"));
		}

	}

	/**
	 * Method attempts to connect to the database given the input provided by the
	 * user.
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private Connection OpenConnection() throws SQLException, ClassNotFoundException {

		// Load the Oracle database driver \
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

		String host = "localhost";
		String port = "1521";
		String dbName = "kevin";
		String userName = "Scott";
		String password = "Tiger123";

		// //Scanner input
		// Scanner reader = new Scanner(System.in);
		// System.out.print("Enter the host name: ");
		// host = reader.next();
		// System.out.print("Enter the port name: ");
		// port = reader.next();
		// System.out.print("Enter the dbName name: ");
		// dbName = reader.next();
		// System.out.print("Enter the userName name: ");
		// userName = reader.next();
		// System.out.print("Enter the password name: ");
		// password = reader.next();
		// reader.close();

		// Construct the JDBC URL
		String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName;
		return DriverManager.getConnection(dbURL, userName, password);
	}

	/**
	 * Close the database connection
	 * 
	 * @param con
	 */
	private void CloseConnection() {

		try {
			connection.close();
		} catch (SQLException e) {
			System.err.println("Cannot close connection: " + e.getMessage());
		}
	}

	public class MainCategory {
		public String mainCategory;
		public HashSet<String> subCategory;

		public MainCategory(String newMainCategory) {
			mainCategory = newMainCategory;
			subCategory = new HashSet<String>();
		}
	}
}
