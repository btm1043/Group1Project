/**
 * Class to handle database operations with regards to users table, inventory table, and history table. 
 */
package combined;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class Database {
	
	/**
	 * Helper class to create an object which has all of the product parameters.  
	 * 
	 */
	protected static class Entry {
		int prodID;
		double price;
		int qty;
		String cat;
		String name;
		
		Entry(int prodID, double price, int qty, String cat, String name) {
			this.prodID = prodID;
			this.price = price;
			this.qty = qty;
			this.cat = cat;
			this.name = name;
		}
		
		int getID() {
			return prodID;
		}
		
		double getPrice() {
			return price;
		}
		
		int getQTY() {
			return qty;
		}
		
		String getCategory() {
			return cat;
		}
		
		String getName() {
			return name;
		}
	}
	
	private static String serverIP = "localhost";
	private static String serverUser = "root";
	private static String serverPass = "gators";
	private static String serverName = "pos";
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://" + serverIP + ":3306/" + serverName + "?autoReconnect=true&useSSL=false";
	private LinkedList<Entry> transaction;
	
	/**
	 * Instantiates an empty transaction list. Currently database only needs to be instantiated when it comes to pushiing a transaction into history table.
	 */
	public Database() {
		transaction = new LinkedList<Entry>();
	}
	
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
		if (user < 1000 || pin < 1000) {
			System.out.println("Please create an account with at least 4 digits for the user and pin!");
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
	 * @param user The user to alter
	 * @param newType The new user mode
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
	 * @param user The user to retrieve user mode
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
	 * Gets the pin from user in user table
	 * 
	 * @param user The user to retrieve pin
	 * 
	 * @return pin of user if success, -1 otherwise
	 */
	public static int getPIN(int user) {
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
			int t =  matches.getInt("pin"); 
			con.close();
			return t;
		} catch (Exception e) {
			System.out.println(e);	
			return -1;
		}
	}
	
	/**
	 * sets the pin of user in user table
	 * 
	 * @param user The user to set pin of
	 * 
	 * @return true if success, false otherwise 
	 */
	public static boolean setPIN(int user, int pin) {
		if (!checkUser(user)) {
			System.out.println("User not found: " + user);
			return false;
		}
		if (user < 0) {
			System.out.println("Invalid pin. pin cannot be negative!");
			return false;
		} 
		try {
			Connection con = getConnection();
			String query = "UPDATE users SET pin = " + pin + " WHERE user=" + user;
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
	 * Checks to see if user is entered into DB.
	 * 
	 * @param user The user to check
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
	 * @param id The id of the product
	 * @param name The name of the product
	 * @param qty Amount of product stocked
	 * @param price The price of the product
	 * @param category The category of the product
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
	 * @param id the id to remove
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
	 * @param id The id of product to set
	 * @param newQty The new quantity of the product
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
	 * @param id Id of product
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
	 * @param id The id of product to set
	 * @param newPrice The new price of the product
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
	 * @param id Id of product
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
	 * @param id The id of the product
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
	 * @param id The id of the product
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
	 * Gets the last transaction ID
	 * 
	 * @return last ID on success. 0 if table is empty, -1 if error.
	 */
	public static int getLastTrans() {
		try {
			Connection con = getConnection();
			String query = "SELECT MAX(trans) FROM history";
			PreparedStatement st = con.prepareStatement(query);
			ResultSet matches = st.executeQuery();
			if (!matches.next()) {
				System.out.println("No entry found ");
				return 0;
			}
			int trans =  matches.getInt(1); 
			con.close();
			return trans;
		} catch (Exception e) {
			System.out.println(e);	
			return -1;
		}
	}
	
	/**
	 * Adds an entry to the transaction linked list. 
	 * 
	 * @param id ID of producct
	 * @param cost Cost of prodct
	 * @param amount Amount of prodct
	 * @param name Name of product
	 * @param cat Category of product
	 */
	public void addEntry(int id, double cost, int amount, String name, String cat ) {
		transaction.add(new Entry(id, cost, amount, cat, name));
	}
	
	
	/**
	 * Logs the transaction to the history table. 
	 * 
	 * @return true if successful, false otherwise. 
	 */
	public boolean logTransaction() {
		if (transaction.size() == 0) {
			System.out.println("Nothing to log!");
			return false;
		}
		int i = transaction.size() - 1;
		int transID = Database.getLastTrans() + 1;
		for (;i >= 0; i--)  {
			Entry n = transaction.removeFirst();
			int prod = n.getID();
			int qt = n.getQTY();
			int available = Database.getQuantity(prod);
			if (available == -1) 
				System.out.println("WARNING: Item sold that is not entered in inventory. Please update inventory!!");
			Database.setQuantity(prod, available - qt);
			if (available > 0 && available-qt < 0)
				System.out.println("WARNING: Sold more items than what's logged in inventory! Please update inventory!");
			Database.addHistoryEntry(transID, prod, n.getName(), n.getPrice(), qt, n.getCategory());	
		}
		return true;
	}
	
	/**
	 * Empties transaction list. Use this when sale gets canceled. 
	 */
	public void clearSale() {
		transaction.clear();
	}
	
	/**
	 * Adds a transaction entry to the history table. This is a helper method for when a transaction is ready to be entered. 
	 * 
	 * @param trans - Transaction ID to be used for lookup
	 * @param id - Product ID to add into table
	 * @param name - Name of product at point of purchase
	 * @param price - Price of product paid at point of purchase
	 * @param qty - Amount of item being bought
	 * @param cat - Category of item at point of purchase
	 * 
	 * @return true on success, false otherwise.
	 */
	private static boolean addHistoryEntry(int trans, int id, String name, double price, int qty, String cat) {
		if (id < 0 || name.length() == 0 || price < 0 || qty <= 0 || cat.length() == 0) {
			System.out.println("No empty strings,negative values, or zero quantities can go into table!");
			return false;
		}
		try {
			Connection con = getConnection();
			String query  = "INSERT INTO history (trans, id,  name, price, qty, category) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, trans);
			st.setInt(2, id);
			st.setString(3, name);
			st.setDouble(4, price);
			st.setInt(5, qty);
			st.setString(6, cat);
			st.execute();
			con.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	/**
	 * Retrieves each history entry with given transaction number
	 * @param trans Trans ID of transction.
	 * 
	 * @return LinkedList with entries that match the Trans ID.
	 */
	public static LinkedList<Entry> getTransaction(int trans) {
		LinkedList<Entry> record = new LinkedList<Entry>();
		try {
			Connection con = getConnection();
			String query = "SELECT * FROM history WHERE trans=" + trans;
			PreparedStatement st = con.prepareStatement(query);
			ResultSet matches = st.executeQuery();
			if (!matches.next()) {
				System.out.println("No record of transaction " + trans);
				return null;
			}
			do {
				record.add(new Entry(matches.getInt(3), matches.getDouble(6), matches.getInt(5), matches.getString(7),  matches.getString(4)));
				
			} while (matches.next());
			
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		return record;
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
