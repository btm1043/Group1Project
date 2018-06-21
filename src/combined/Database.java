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
	
	public static void setServerName(String name) {
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
			String query  = "INSERT INTO users (user, pin, type) VALUES (?, ?, ?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, user);
			st.setInt(2, pin);
			st.setInt(3, type);
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
					System.out.println("Error. Multiple users with same id.");
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
	
	
	
	public static boolean addProduct(int id, String name, double price, String category) {
		if (price < 0 || id < 0) {
			System.out.println("Product ID and price must be non-negative!");
			return false;
		}
		if (category.length() == 0 || name.length() == 0) {
			System.out.println("You cannot enter empty names or categories into the database!");
			return false;
		}
		if (checkProduct(id)) {
			System.out.println("Product already exists in table!");
			return false;
		}
		try {
			Connection con = getConnection();
			String query  = "INSERT INTO products (id, name, price, category) VALUES (?, ?, ?, ?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, id);
			st.setString(2, name);
			st.setDouble(3, price);
			st.setString(4,  category);
			st.execute();
			con.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public static boolean removeProduct(int id) {
		if (!checkProduct(id)) {
			System.out.println("Product not found: " + id);
			return false;
		}
		try {
			Connection con = getConnection();
			String query  = "DELETE FROM products WHERE id=" + id;
			PreparedStatement st = con.prepareStatement(query);
			st.executeUpdate();
			con.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	
	public static boolean setPrice(int id, double newPrice) {
		if (!checkProduct(id)) {
			System.out.println("Product not found: " + id);
			return false;
		}
		if (newPrice < 0) {
			System.out.println("Invalid price. Please enter a positive price for the product.");
			return false;
		}
		try {
			Connection con = getConnection();
			String query = "UPDATE products SET price = " + newPrice + " WHERE id=" + id;
			PreparedStatement st = con.prepareStatement(query);
			st.executeUpdate();
			
			con.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public static double getPrice(int id) {
		try {
			Connection con = getConnection();
			String query = "SELECT * FROM products WHERE id=?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, id);
			ResultSet matches = st.executeQuery();
			if (!matches.next()) {
				System.out.println("Product not found: " + id);
				return -1;
			}
			double t =  matches.getDouble("price"); 
			con.close();
			return t;
		} catch (Exception e) {
			System.out.println(e);	
			return -1;
		}
	}
	
	public static String getProductCategory(int id) {
		try {
			Connection con = getConnection();
			String query = "SELECT * FROM products WHERE id=?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, id);
			ResultSet matches = st.executeQuery();
			if (!matches.next()) {
				System.out.println("Product not found: " + id);
				return "Not found!";
			}
			String name =  matches.getString("category"); 
			con.close();
			return name;
		} catch (Exception e) {
			System.out.println(e);	
			return "Not found!";
		}
	}
	
	public static String getProductName(int id) {
		try {
			Connection con = getConnection();
			String query = "SELECT * FROM products WHERE id=?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, id);
			ResultSet matches = st.executeQuery();
			if (!matches.next()) {
				System.out.println("Product not found: " + id);
				return "Not found!";
			}
			String name =  matches.getString("name"); 
			con.close();
			return name;
		} catch (Exception e) {
			System.out.println(e);	
			return "Not found!";
		}
	}
	
	public static boolean checkProduct(int id) {
		
		try {
			Connection con = getConnection();
			ResultSet matches;
			String query = "SELECT COUNT(*) FROM products WHERE id = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, id);
			matches = st.executeQuery();
			
			if(matches.next()) {
				int count = matches.getInt(1);
				if (count > 1) {
					System.out.println("Error. Multiple products with same id.");
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
			System.out.println("Connection Failed.\n" + e);
			return null;
		}
	}

}
