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
	
	/**
	 * Sets the ip of DB server
	 * 
	 * @param ip new ip
	 */
	public static void setIP(String ip) {
		serverIP = ip;
	}
	
	/**
	 * Sets the user for DB
	 * 
	 * @param user New username for DB
	 */
	public static void setUser(String user) {
		serverUser = user;
	}
	
	/**
	 * Sets the password for DB
	 * 
	 * @param pass New password for Username
	 */
	public static void setPass(String pass) {
		serverPass = pass;
	}
	
	/**
	 * Sets the name of server
	 * 
	 * @param name New server name
	 */
	public static void setServerName(String name) {
		serverName = name;
	}
	
	/**
	 * Reconstructs DB URL
	 */
	public static void setURL() {
		url = "jdbc:mysql://" + serverIP + ":3306/" + serverName + "?autoReconnect=true&useSSL=false";
	}
	
	/**
	 * Manually sets new URL
	 * 
	 * @param newURL The new DB URL
	 */
	public static void setURL(String newURL) {
		url = newURL;
	}
	
	
	/**
	 * Adds a user/pin/manager entry into the users table.
	 * 
	 * @param user The user to be added
	 * @param pin The pin to be tied to user
	 * @param type Manager type tied to user
	 * 
	 * @return true if successful, false if unsuccessful.
	 */
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
	
	/**
	 * Removes user from user table.
	 * 
	 * @param user The user to be removed from user table
	 * 
	 * @return true if successful, false otherwise.
	 */
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
	
	/**
	 * Sets the user type associated with given user in user table.
	 * 
	 * @params user The user to alter
	 * @params newType The new user mode
	 * 
	 * @return true if successful, false otherwise.
	 */
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
	
	/**
	 * Gets the user mode from user in user table
	 * 
	 * @params user The user to retrieve user mode
	 * 
	 * @return user mode of user (0/1), or -1 if unsuccessful 
	 */
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
	
	/**
	 * Checks to see if user is entered into DB.
	 * 
	 * @params user The user to check
	 * 
	 * @return true if found, false otherwise
	 */
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
	
	/**
	 * Adds a product to inventory table
	 * 
	 * @params id The id of the product
	 * @params name The name of the product
	 * @params qty Amount of product stocked
	 * @params price The price of the product
	 * @params category The category of the product
	 * 
	 * @return true if successful, false otherwise
	 */
	public static boolean addProduct(int id, String name, int qty, double price, String category) {
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
			String query  = "INSERT INTO inventory (id, name, qty, price, category) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, id);
			st.setString(2, name);
			st.setInt(3, qty);
			st.setDouble(4, price);
			st.setString(5,  category);
			st.execute();
			con.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	/**
	 * Adds a new item to the inventory table 
	 * @param id
	 * @param name
	 * @param price
	 * @param category
	 * @return
	 */
	public static boolean addProduct(int id, String name, double price, String category) {
		return addProduct(id, name, 0, price, category);
	}
	
	/**
	 * Removes product from inventory table completely
	 * 
	 * @params id the id to remove
	 * 
	 * @return true if successful, false otherwise. 
	 */
	public static boolean removeProduct(int id) {
		if (!checkProduct(id)) {
			System.out.println("Product not found: " + id);
			return false;
		}
		try {
			Connection con = getConnection();
			String query  = "DELETE FROM inventory WHERE id=" + id;
			PreparedStatement st = con.prepareStatement(query);
			st.executeUpdate();
			con.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	
	/**
	 * Sets the quantity of a product available in the inventory table.
	 * 
	 * @params id The id of product to set
	 * @params newQty The new quantity of the product
	 * 
	 * @return true if successful, false otherwise
	 */
	public static boolean setQuantity(int id, int newQty) {
		if (!checkProduct(id)) {
			System.out.println("Product not found: " + id);
			return false;
		}
		if (newQty < 0) {
			System.out.println("Invalid quantity. Please enter a positive quantity for the product.");
			return false;
		}
		try {
			Connection con = getConnection();
			String query = "UPDATE inventory SET qty = " + newQty + " WHERE id=" + id;
			PreparedStatement st = con.prepareStatement(query);
			st.executeUpdate();
			
			con.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	/**
	 * Gets the quantity of product by ID
	 * 
	 * @params id Id of product
	 * 
	 * @return quantity of product left on success, -1 otherwise
	 */
	public static int getQuantity(int id) {
		try {
			Connection con = getConnection();
			String query = "SELECT * FROM inventory WHERE id=?";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, id);
			ResultSet matches = st.executeQuery();
			if (!matches.next()) {
				System.out.println("Product not found: " + id);
				return -1;
			}
			int t =  matches.getInt("qty"); 
			con.close();
			return t;
		} catch (Exception e) {
			System.out.println(e);	
			return -1;
		}
	}
	
	
	/**
	 * Sets the price of a product
	 * 
	 * @params id The id of product to set
	 * @params newPrice The new price of the product
	 * 
	 * @return true if successful, false otherwise
	 */
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
			String query = "UPDATE inventory SET price = " + newPrice + " WHERE id=" + id;
			PreparedStatement st = con.prepareStatement(query);
			st.executeUpdate();
			
			con.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	/**
	 * Gets the price of product by ID
	 * 
	 * @params id Id of product
	 * 
	 * @return price of product, -1 otherwise
	 */
	public static double getPrice(int id) {
		try {
			Connection con = getConnection();
			String query = "SELECT * FROM inventory WHERE id=?";
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
	
	/**
	 * Returns the category of the product id
	 * 
	 * @params id The id of the product
	 * 
	 * @return The category of the product id if successful, "Not found!" otherwise
	 */
	public static String getProductCategory(int id) {
		try {
			Connection con = getConnection();
			String query = "SELECT * FROM inventory WHERE id=?";
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
	
	/**
	 * Gets the name of a product
	 * 
	 * @params id The id of the product
	 * 
	 * @returns The name of the product. "Not found!" otherwise
	 */
	public static String getProductName(int id) {
		try {
			Connection con = getConnection();
			String query = "SELECT * FROM inventory WHERE id=?";
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
	
	/**
	 * Checks to see if product is entered into inventory table
	 * 
	 * @params id The id of product
	 * 
	 * @return true if found, false otherwise.
	 */
	public static boolean checkProduct(int id) {
		
		try {
			Connection con = getConnection();
			ResultSet matches;
			String query = "SELECT COUNT(*) FROM inventory WHERE id = ?";
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

	/**
	 * Helper method to secure connection to DB. 
	 */
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
