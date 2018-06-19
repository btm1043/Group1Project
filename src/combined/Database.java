package combined;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {
	private static String serverIP = "localhost";
	private static String serverUser = "root";
	private static String serverPass = "gators";
	private static String serverName = "pos";
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://" + serverIP + ":3306/" + serverName + "?autoReconnect=true&useSSL=false";
	
	public static void setIP(String ip) {
		serverIP = ip;
	}
	
	public static void setUser(String user) {
		serverUser = user;
	}
	
	public static void setPass(String pass) {
		serverPass = pass;
	}
	
	public static void setName(String name) {
		serverName = name;
	}
	
	public static void setURL() {
		url = "jdbc:mysql://" + serverIP + ":3306/" + serverName + "?autoReconnect=true&useSSL=false";
	}
	
	public static void setURL(String newURL) {
		url = newURL;
	}
	
	public static boolean addUser(int user, int pin, int type) {
		if (type < 0 || type > 1) {
			System.out.println("Please enter a 1 for a manager user, or a 0 for a normal user.");
			return false;
		}
		if (checkUser(user)) {
			System.out.println("User number already exists.");
			return false;
		}
		try {
			Connection con = getConnection();
			String query  = "INSERT INTO users (type, user, pin) VALUES (?, ?, ?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, type);
			st.setInt(2, user);
			st.setInt(3, pin);
			st.execute();
			con.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public static boolean removeUser(int user) {
		if (!checkUser(user)) {
			System.out.println("User not found: " + user);
			return false;
		}
		try {
			Connection con = getConnection();
			String query  = "DELETE FROM users WHERE user=" + user;
			PreparedStatement st = con.prepareStatement(query);
			st.executeUpdate();
			con.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	
	public static boolean setType(int user, int newType) {
		if (!checkUser(user)) {
			System.out.println("User not found: " + user);
			return false;
		}
		if (newType < 0 || newType > 1) {
			System.out.println("Invalid type. Please enter 1 for manager user, or 0 for regular user.");
			return false;
		}
		try {
			Connection con = getConnection();
			String query = "UPDATE users SET type = " + newType + " WHERE user=" + user;
			PreparedStatement st = con.prepareStatement(query);
			st.executeUpdate();
			
			con.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public static int getType(int user) {
		try {
			Connection con = getConnection();
			String query = "SELECT * FROM users WHERE user=?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, user);
			ResultSet matches = st.executeQuery();
			if (!matches.next()) {
				System.out.println("User not found: " + user);
				return -1;
			}
			int t =  matches.getInt("type"); 
			con.close();
			return t;
		} catch (Exception e) {
			System.out.println(e);	
			return -1;
		}
	}
	
	public static boolean checkUser(int user) {
		
		try {
			Connection con = getConnection();
			ResultSet matches;
			String query = "SELECT COUNT(*) FROM users WHERE user = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, user);
			matches = st.executeQuery();
			
			if(matches.next()) {
				int count = matches.getInt(1);
				if (count > 1) {
					System.out.println("Error. Multiple entires with same information.");
					return false;
				}
				return count == 1;
				
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		} 
	}
	
	private static Connection getConnection() {
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, serverUser, serverPass);
			return con;
		} catch (Exception e) {
			System.out.println("Connection Failed. " + e);
			return null;
		}
	}
	
	

}
