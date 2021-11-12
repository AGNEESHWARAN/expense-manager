package splitwise;
import java.sql.*;


public class DatabaseManager {

	static final String DB_URL = "jdbc:mysql://localhost/splitwise";
	static final String USER = "guest";
	static final String PASS = "Guest@123+";
	
	static Connection conn;
	
	public static void connectToDB() throws SQLException {
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
	}
	
	public  static int addRecord(String sql) throws SQLException {
		//conn = DriverManager.getConnection(DB_URL, USER, PASS);
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
		ResultSet generatedKeys = stmt.getGeneratedKeys();
		generatedKeys.next();
		return generatedKeys.getInt(1);
	}
	
	public static ResultSet getRecords(String sql) throws SQLException {
		//conn = DriverManager.getConnection(DB_URL, USER, PASS);
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	
		ResultSet result = stmt.executeQuery(sql);
		return result;
		
	}
	
	public static void updateRecord(String sql) throws SQLException {
		//conn = DriverManager.getConnection(DB_URL,USER,PASS);
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);

	}
	
	public static void closeConnection() throws SQLException {

		conn.close();
	}
	
	public static String insertSQL(CustomList lis ,String tableName ) {
		String sql = "";
		String parm="(",val="(";
		
		sql="insert into "+tableName;
		for(Integer i=0;i<lis.size;i++) {
			if(i+1 !=lis.size)
				parm+=lis.getByIndex(i).field+",";
			else
				parm+=lis.getByIndex(i).field+"";
			if(lis.getByIndex(i).type.equals("int")) {
				if(i+1 !=lis.size)
					val+="'"+lis.getByIndex(i).intVal+"',";
				else
					val+="'"+lis.getByIndex(i).intVal+"'";
			}else if(lis.getByIndex(i).type.equalsIgnoreCase("string")) {
				if(i+1 !=lis.size)
					val+="'"+lis.getByIndex(i).stringVal+"',";
				else
					val+="'"+lis.getByIndex(i).stringVal+"'";
			}else if(lis.getByIndex(i).type.equalsIgnoreCase("double")) {
				if(i+1 !=lis.size)
					val+="'"+lis.getByIndex(i).doubleVal+"',";
				else
					val+="'"+lis.getByIndex(i).doubleVal+"'";
			}
			
		}
			parm+=")";
			val+=")";
			sql+=parm +" values"+val;
		
		
		return sql;
	}
	
}