package database;
import java.sql.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
	
	public ArrayList<String> getCharacterStats(String charID){
        //returns all the stats for a character given an id
        try {
            ArrayList<String> stats = new ArrayList<String>();
        
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM character WHERE id = '" + charID.trim() + "'");
        
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
	
	public int checkPlayerType(String charID){
        // return list of all team names
        try {        
            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();
            Statement stmt3 = con.createStatement();
            ResultSet spyRS = stmt1.executeQuery("SELECT c.id FROM character c, spy s WHERE c.id = s.id AND c.id = " + charID);
            ResultSet poliRS = stmt2.executeQuery("SELECT c.id FROM character c, politician p WHERE c.id = p.id AND c.id = " + charID);
            ResultSet bizRS = stmt3.executeQuery("SELECT c.id FROM character c, businessman b WHERE c.id = b.id AND c.id = " + charID);
            
            int t = 0;
            
            // query works, gets the correct 'type' for each player but checking if the ResultSets are empty does not work
            if (!spyRS.next()) {
            	if (!poliRS.next()) {
            		t = 3;
            	} else {
            		t = 2;
            	}
            } else {
            	t = 1;
            }
            return t;
        } catch (Exception e) {
            System.err.println("Got a checkPlayerType() exception! ");
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

	public ArrayList<String> getEnemiesList(String charID) {
		try {
			System.out.println("In getEnemiesList query top...");
			
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

	public void deleteCharacter(int victim) {
		// delete character when they are killed
		try {
			Statement stmt = con.createStatement();
			String queryString = "DELETE FROM character WHERE id = " + victim;
			ResultSet rs = stmt.executeQuery(queryString);
		} catch (Exception e) {
            System.err.println("Got a deleteCharacter exception!");
            System.err.println(e.getMessage());
            return;
        }
        
	}

	public ArrayList<String> getPoliList() {
		// get list of all politicians
		try {
            ArrayList<String> politicians = new ArrayList<String>();
        
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM politician");
        
            while (rs.next()) {
            	politicians.add(rs.getString("id"));
            	politicians.add(rs.getString("name"));
            	politicians.add(rs.getString("popularity"));
            }
            
            return politicians;
            
        } catch (Exception e) {
            System.err.println("Got getPoliList() exception! ");
            System.err.println(e.getMessage());
            return null;
        }
	}

	public ArrayList<String> getSpyList() {
		// get list of all spies
		try {
            ArrayList<String> spies = new ArrayList<String>();
        
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM spy");
        
            while (rs.next()) {
            	spies.add(rs.getString("id"));
            	spies.add(rs.getString("name"));
            	spies.add(rs.getString("success"));
            }
            System.out.println(spies);
            return spies;
            
        } catch (Exception e) {
            System.err.println("Got getSpyList() exception! ");
            System.err.println(e.getMessage());
            return null;
        }
	}
	
	public void transferMoney(String to, String from, int amt) {
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select id, cash from character where id = '" + to
																					+ "' or id = '" + from + "'");
			
			ArrayList<String> characterTo = new ArrayList<String>();
			ArrayList<String> characterFrom = new ArrayList<String>();
			
			while (rs.next()){
				if (rs.getString("id").equals(to)){
				characterTo.add(rs.getString("id"));
				characterTo.add(rs.getString("cash"));
				}
				else if (rs.getString("id").equals(from)){
				characterFrom.add(rs.getString("id"));
				characterFrom.add(rs.getString("cash"));
				}
			}
		 if (Integer.getInteger(characterFrom.get(1)) >= amt){
			 Statement transfer = con.createStatement();
			 transfer.executeQuery("UPDATE character"
			 						+ "SET cash = " + (Integer.getInteger(characterTo.get(1)) + amt)
			 						+ " WHERE id = '" + characterTo.get(0) + "'");
			 
			 Statement debit = con.createStatement();
			 debit.executeQuery("UPDATE character"
			 					+ "SET cash = " + (Integer.getInteger(characterFrom.get(1)) - amt)
			 					+ "WHERE id = '" + characterFrom.get(0) + "'");
		 }
		 else {
			 throw new Exception();
		 }
			
		}
		catch (Exception e){
			System.err.println("Unable to Process Transaction - Not Enough Cash");
			
		}
		
	}

	public void createCampaign(String charID, String type) {
		// TODO update politician charID's cash and popularity based on type of marketing campaign (cost and effect)
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, cash, popularity FROM character c, politician p WHERE p.id = c.id AND c.id = '" + charID + "'");
			
			ArrayList<String> character = new ArrayList<String>();
			
			while (rs.next()){
				character.add(rs.getString("id"));
				character.add(rs.getString("cash"));
				character.add(rs.getString("popularity"));
			}
            int cash = Integer.getInteger(character.get(1));
            int pop = Integer.getInteger(character.get(2));
            int newcash;
            double newpop;
            
            if (type == "Type 1") {
            	newcash = cash - 200;
            	newpop = pop + 50;
            } else if (type == "Type 2") {
            	newcash = cash - 100;
            	newpop = pop + 25;
            } else if (type == "Type 3") {
            	newcash = cash - 50;
            	newpop = pop + 12;
            } else {
            	if (type == "Type 4") {
            		newcash = cash - 25;
            		newpop = pop + 5;
            	} else {
            		System.err.println("Got error in createCampaign()");
                	newcash = cash;
                	newpop = pop;
            	}
            }
            
            Statement update = con.createStatement();
            update.executeQuery("UPDATE character SET cash = " + newcash + " WHERE id = '" + character.get(0) + "'");  
            update.executeQuery("UPDATE politician SET popularity = " + newpop + " WHERE id = '" + character.get(0) + "'");  
        } catch (Exception e) {
            System.err.println("Got createCampaign() exception! ");
            System.err.println(e.getMessage());
        }
	}

	public void giveSpeech(String charID, int cost) {
		//  update politician charID's cash and popularity based on type of speech (cost and effect)
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT c.id, c.cash, p.popularity FROM character c, politician p WHERE p.id = c.id AND c.id = '" + charID + "'");
			
			ArrayList<String> character = new ArrayList<String>();
			
			while (rs.next()){
				character.add(rs.getString("id"));
				character.add(rs.getString("cash"));
				character.add(rs.getString("popularity"));
			}
            int cash = Integer.getInteger(character.get(1));
            int pop = Integer.getInteger(character.get(2));
            int newcash;
            double newpop;
            
            if (cost == 120) {
            	newcash = cash - 120;
            	newpop = pop + 25;
            } else if (cost == 60) {
            	newcash = cash - 60;
            	newpop = pop + 12;
            } else if (cost == 30) {
            	newcash = cash - 30;
            	newpop = pop + 6;
            } else {
            	if (cost == 10) {
            		newcash = cash - 10;
            		newpop = pop + 2;
            	} else {
            		System.err.println("Got error in giveSpeech(), no changes");
                	newcash = cash;
                	newpop = pop;
            	}
            }
            
            Statement update = con.createStatement();
            update.executeQuery("UPDATE character SET cash = " + newcash + " WHERE id = '" + character.get(0) + "'");  
            update.executeQuery("UPDATE politician SET popularity = " + newpop + " WHERE id = '" + character.get(0) + "'");  
        } catch (Exception e) {
            System.err.println("Got giveSpeech() exception! ");
            System.err.println(e.getMessage());
        }
	}

	public void getActions(String victim) {
		// for spy, get list of actions of victim
		try {
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM action WHERE id = " + victim + ")";
			ResultSet rs = stmt.executeQuery(query);
			
			ArrayList<String> actions = new ArrayList<String>();
			
			while (rs.next()){
				actions.add(rs.getString("id"));
				actions.add(rs.getString("time"));
				actions.add(rs.getString("type"));
			}
		} catch (SQLException e) {
			System.err.println("Got getActions() exception! ");
            System.err.println(e.getMessage());
		}
	}

	public void logAction(String charID, int type) {
		// update action table with entry value(charID, time, type)
		try {
			long time = System.currentTimeMillis();
			Statement stmt = con.createStatement();
			String query = "INSERT INTO action values( '" + charID + "', '" + time + "', '" + type + "')";
			ResultSet rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			System.err.println("Got logAction() exception! ");
            System.err.println(e.getMessage());
		}
	}
}