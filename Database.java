import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {
	private String serverIP = "localhost";
	private String serverUser = "root";
	private String serverPass = "gators";
	private String serverName = "pos";
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://" + serverIP + ":3306/" + serverName + "?autoReconnect=true&useSSL=false";
	
	
	
	
	public boolean addUser(int user, int pin) {
		try {
			Connection con = getConnection();
			String query  = "insert into users (user, pin) values (?, ?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, user);
			st.setInt(2, pin);
			st.execute();
			con.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public boolean removeUser(int user) {
		try {
			Connection con = getConnection();
			String query  = "delete from users where user=" + user;
			PreparedStatement st = con.prepareStatement(query);
			st.execute();
			con.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public boolean checkUser(int user, int pin) {
		
		try {
			Connection con = getConnection();
			ResultSet matches;
			String query = "SELECT count(*) from users WHERE user = ? AND pin = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, user);
			st.setInt(2, pin);
			matches = st.executeQuery();
			
			if(matches.next()) {
				int count = matches.getInt(1);
				if (count > 1) {
					System.out.println("Error. Multiple entires with same information.");
				} else if (count == 1)
					return true;
			}
		} catch (Exception e) {
			System.out.println(e);;
		} 
		return false;
	}
	
	private Connection getConnection() {
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, serverUser, serverPass);
			System.out.println("Connected!");
			return con;
		} catch (Exception e) {
			System.out.println("Connection Failed. " + e);
		}
		return null;
	}

}
