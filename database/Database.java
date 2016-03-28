package database;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class Database {
	private Connection con;
	private String email;
	private String username;
	private String password;
	
	public Database(String username, String password){
		// Initialize database variables
		this.username = username;
		this.password = password;
		
		// connect to server
		this.connectToDatabase(this.username, this.password);
	}
	
	public boolean authenticateLogin(String username, String password){
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM player");
        
            String email = new String();
            String pass = new String();
            while (rs.next()) {
                email = rs.getString("username").trim();
                pass = rs.getString("password").trim();
                
                if (email.equals(username) && password.equals(pass)) {
                    return true;
                }
            }
            return false;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            return false;
        }
	}
	
	public boolean authenticateAdmin(String username2, String password2) {
		try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM player");
            
            while (rs.next()){
            	email = rs.getString("username").trim();
            	int admin = rs.getInt("admin");
            	if (admin != 0 && email.equals(username2)){
            	System.out.println("ADMIN");
            	return true;
            	}

            }
        	System.out.println("NAADMIN");
        	return false;
		}
		catch (SQLException e){
			return false;
		}
	}
	
	public boolean authenticateSignup(String email, String username, String password){
		
		try {
			if (checkUsername(email, username)){
				return false;
			}
			createAccount(email, username, password);
			return true;
		}
		catch (SQLException e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            return false;
			
		}
		
	}
	
	private boolean checkUsername(String email2, String username2) throws SQLException {
		Statement stmt = con.createStatement();
        String queryString = "select * from player";
        ResultSet rs = stmt.executeQuery(queryString);
        
        while(rs.next()){
            email = rs.getString("email").trim();
            username = rs.getString("username").trim();
            
            if (email.equals(email2) || username.equals(username2)) {
                return true;
            }
        	
        }
		return false;
	}

	private void createAccount(String email, String username, String password) throws SQLException {
        Statement stmt = con.createStatement();
        String queryString = "insert into player values(" + "'" + email + "', "
        												+ "'" + username + "', "
        												+ "'" + password + "'" + ")";
        ResultSet rs = stmt.executeQuery(queryString);
        
	}

	public ArrayList<String> getPlayerList(){
        // returns a list of all players by username
        try {
            ArrayList<String> players = new ArrayList<String>();
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username FROM player");
        
            while (rs.next()) {
                players.add(rs.getString("username"));
            }
        
            return players;
            
        } catch (Exception e) {
            System.err.println("Got a PLayerList exception! ");
            System.err.println(e.getMessage());
            return null;
        }
	}
	
	public ArrayList<String> getCharList(String usrname){
		// return list of a player's characters
		try {
            ArrayList<String> chars = new ArrayList<String>();
            
            Statement stmt = con.createStatement();
            String query = "select character.id, character.name, character.lvl "
            				+ "From character, player "
            				+ "Where player.username = " + "'" + usrname + "' " + "and player.email = character.email";
            ResultSet rs = stmt.executeQuery(query);
        
            while (rs.next()) {
                chars.add(rs.getString("id"));
                chars.add(rs.getString("name"));
                chars.add(rs.getString("lvl"));
            }
            
            return chars;
            
        } catch (Exception e) {
            System.err.println("Got a CharList exception! ");
            System.err.println(e.getMessage());
            return null;
        }
	}
	
	public ArrayList<String> getCharacterStats(int id){
        //returns all the stats for a character given an id
        try {
            ArrayList<String> stats = new ArrayList<String>();
        
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM character WHERE id = " + id);
        
            while (rs.next()) {
                stats.add(rs.getString("id"));
                stats.add(rs.getString("name"));
                stats.add(rs.getString("lvl"));
                stats.add(rs.getString("cash"));
                stats.add(rs.getString("email"));
                stats.add(rs.getString("teamName"));
            }
            
            return stats;
            
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            return null;
        }
	}
	
	public ArrayList<String> getLog(int id){
        // return list of a characters actions
        try {
            ArrayList<String> log = new ArrayList<String>();
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM action WHERE id = " + id);
            
            while (rs.next()) {
                log.add(rs.getString("id"));
                log.add(rs.getString("time"));
                log.add(rs.getString("action"));
            }
            
            return log;
            
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            return null;
        }
	}
	
	public ArrayList<String> getTeamsandNumberofPlayers(){
        // return list of all team names
        try {
            ArrayList<String> teams = new ArrayList<String>();
        
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT t.name, c.count(*) FROM team t, character c WHERE t.name = c.teamName");
        
            while (rs.next()) {
                teams.add(rs.getString("name"));
            }
            
            return teams;
            
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            return null;
        }
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