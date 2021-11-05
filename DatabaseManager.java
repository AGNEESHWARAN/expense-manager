package splitwise;
import java.sql.*;


public class DatabaseManager {

	static final String DB_URL = "jdbc:mysql://localhost/splitclone";
	static final String USER = "guest";
	static final String PASS = "Guest@123+";
	
	static Connection conn;
	
	public static void connectToDB() throws SQLException {
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
	}
	
	public int addRecord(String sql) throws SQLException {
		//conn = DriverManager.getConnection(DB_URL, USER, PASS);
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
		ResultSet generatedKeys = stmt.getGeneratedKeys();
		generatedKeys.next();
		return generatedKeys.getInt(1);
	}
	
	public ResultSet getRecords(String sql) throws SQLException {
		//conn = DriverManager.getConnection(DB_URL, USER, PASS);
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	
		ResultSet result = stmt.executeQuery(sql);
		return result;
		
	}
	
	public void updateRecord(String sql) throws SQLException {
		//conn = DriverManager.getConnection(DB_URL,USER,PASS);
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);

	}
	
	public static void closeConnection() throws SQLException {

		conn.close();
	}
	
}