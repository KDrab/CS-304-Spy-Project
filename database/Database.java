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
	
	public ArrayList<String> getTeams(){
        // return list of all team names
        try {
            ArrayList<String> teams = new ArrayList<String>();
        
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM team");
        
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
	
	public ArrayList<String> getTeamStats(String team){
        // return list of all team names
        try {
            ArrayList<String> stats = new ArrayList<String>();
            stats.add(team);
        
            System.out.println("getTeamStats()...");

            String q1 = "SELECT count(*) as total FROM character WHERE teamName = '" + team + "'";
            String q2 = "SELECT avg(lvl) as avglvl FROM character WHERE teamName = '" + team + "'";
            String q3 = "SELECT sum(cash) as allCash FROM character WHERE teamName = '" + team + "'";
            
            System.out.println("queries created.");
            
            Statement stmt = con.createStatement();
            ResultSet rs1 = stmt.executeQuery(q1);
            
            System.out.println("q1 done.");
            
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery(q2);
            
            System.out.println("q2 done.");
            
            Statement stmt3 = con.createStatement();
            ResultSet rs3 = stmt3.executeQuery(q3);
            
            System.out.println("All q done. All RS created.");
            
            while (rs1.next()) {
                stats.add(rs1.getString("total"));
            }
            
            while (rs2.next()) {
                stats.add(rs2.getString("avglvl"));
            }
            
            while (rs3.next()) {
                stats.add(rs3.getString("allCash"));
            }
            
            System.out.println("stats[] created.");
            
            return stats;
            
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            return null;
        }
	}
	
	public int checkPlayerType(int id){
        // return list of all team names
        try {        
            Statement stmt = con.createStatement();
            ResultSet spyRS = stmt.executeQuery("SELECT c.id FROM character c, spy s WHERE c.id = s.id");
            ResultSet poliRS = stmt.executeQuery("SELECT c.id FROM character c, politician p WHERE c.id = p.id");
            ResultSet bizRS = stmt.executeQuery("SELECT c.id FROM character c, businessman b WHERE c.id = b.id");
            
            System.out.println("Spy? " + spyRS.next() + "; Poli? " + poliRS.next() + "; Biz? " + bizRS.next());
            
            if (spyRS.next()) {
            	return 1;
            } else if (poliRS.next()) {
            	return 2;
            } else {
            	return 3;
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            return 0;
        }
	}
	
	public ArrayList<String> getLeaderBoard(int i) {
		try {
            ArrayList<String> leaders = new ArrayList<String>();
            
        
            System.out.println("Creating queries...");
            
            String minMax;
            
            if (i == 0) {
            	minMax = "SELECT max(avglvl) as lvl FROM (SELECT teamName, avg(lvl) as avglvl FROM character GROUP BY teamName)";
            } else if (i == 1) {
            	minMax = "SELECT min(avglvl) as lvl FROM (SELECT teamName, avg(lvl) as avglvl FROM character GROUP BY teamName)";
            } else {
            	System.out.println("Invalid LeaderBoard query!");
            	minMax = "";
            	return null;
            }
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(minMax);
            
    		System.out.println("Query round 1 done.");
            while (rs.next()) {
                leaders.add(rs.getString("lvl"));
            }
            
            System.out.println("leaders[] half full.");
            
            String lvl = leaders.get(0);
            
            String query = "SELECT teamName, avg(lvl) as avglvl FROM character GROUP BY teamName HAVING avg(lvl) = " + lvl;
            
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery(query);
            
    		System.out.println("Query round 2 done.");
            while (rs2.next()) {
            	leaders.add(rs2.getString("teamName"));
            }
            
            System.out.println("leaders[] full.");
            return leaders;
            
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

	public ArrayList<String> getEnemiesList(int charID) {
		try {
            ArrayList<String> enemies = new ArrayList<String>();
            
            System.out.println("getting enemies list");
        
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name, lvl FROM character WHERE id <> " + charID);
            
            System.out.println("getting enemies list - query done");
        
            while (rs.next()) {
            	enemies.add(rs.getString("id"));
            	enemies.add(rs.getString("name"));
            	enemies.add(rs.getString("lvl"));
            }
            
            System.out.println("getting enemies list - returning enemies");
            
            return enemies;
            
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            return null;
        }
	}
}