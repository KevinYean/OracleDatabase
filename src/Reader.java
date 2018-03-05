import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**Class meant for reading a file and saving it to a string.
 * 
 * @author Kevin Yean
 *
 */
public class Reader {
	
	private String fileString;
	
	public Reader() {
		fileString = "";
	}
	
	public void ReadFile(String path) throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			}
		    fileString = sb.toString();
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
