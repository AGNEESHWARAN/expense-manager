package splitwise;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;


public class UserManager  {
	final Scanner scan = new Scanner(System.in);
	DatabaseManager dm = new DatabaseManager();
	
	static final String DB_URL = "jdbc:mysql://localhost/splitwise";
	static final String USER = "guest";
	static final String PASS = "Guest@123+";
	

	
	
	public int addUser(User user) throws SQLException {
		String sql="insert into User(user_name,age,phone_number,gender,email) values('"+user.getName()+"',"
				+ "'"+user.getAge()+"',"
						+ "'"+user.getMobileNumber()+"',"
								+ "'"+user.getGender()+"',"
										+ "'"+user.getEmailId()+"')";
			
		
	
		return dm.addRecord(sql);
	}

	public User getUserById(int id) throws SQLException {
		User user = null;
        String sql="select * from User where user_id="+id;
        ResultSet rs = dm.getRecords(sql);
		boolean result=rs.next();
		if(result) {
			user = new  User(rs.getInt(1),
					rs.getString(2),
					rs.getInt(3),
					rs.getString(5),
					rs.getString(4),
					rs.getString(6));
		}
		return user;
		
	}
	
	public ArrayList<User> getAllUsers() throws SQLException{
		ArrayList<User> users = new ArrayList<User>();
		User user=null;
		String sql ="select * from User";
		
		
		
		ResultSet rs = dm.getRecords(sql);
		while(rs.next()) {
			user = new  User(rs.getInt(1),
					rs.getString(2),
					rs.getInt(3),
					rs.getString(5),
					rs.getString(4),
					rs.getString(6));
			users.add(user);
		}
		return users;
	}
	
	
	
}



