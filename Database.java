import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {
	private Connection con;
	private String username;
	private String password;
	
	public Database(String username, String password){
		// Initialize database variables
		this.username = username;
		this.password = password;
		
		// connect to server
		this.connectToDatabase(this.username, this.password);
	}
	

	public void connectToDatabase(String username, String password){
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return;

		}

		System.out.println("Oracle JDBC Driver Registered!");

		try {

			this.con = DriverManager.getConnection(
					  "jdbc:oracle:thin:@localhost:1522:ug", "ora_q0b9", "a48197123");

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}

		if (con != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}
}