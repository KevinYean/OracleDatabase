import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	
	//Holds a string of the a file value.
	private String fileString;
	
    public static void main( String[] args ) throws IOException, JSONException {
    	
        Populate populate = new Populate();
        System.out.println("Starting populate.java");
        	
        populate.ReadFile(".//src/yelp_user.json");
        
        for(String fileName: args) {
        	System.out.println(fileName);
        	populate.ReadFile(fileName);
        }
        
        System.out.println("Finished populating tables");
        System.exit( 0 ); //success
    }
    
    /**
     * This method reads any type of file and stores its content into fileString
     * @param path
     * @throws IOException
     * @throws JSONException 
     */
    public void ReadFile(String path) throws IOException, JSONException {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			StringBuilder sb = new StringBuilder();

			//Sample
			String str = "{\"user_id\": \"qtrmBGNqCvupHMHL_bKFgQ\"}";
			JSONObject obj = new JSONObject(str);
			String n = obj.getString("user_id");
			System.out.println(n + " ");  // prints "Alice 20"
			
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
	 * Method returns fileString
	 * @return
	 */
	public String GetFileString() {
		return fileString;
	}
}
