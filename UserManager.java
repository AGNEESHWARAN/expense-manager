package splitwise;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;


public class UserManager  {
	final Scanner scan = new Scanner(System.in);
	CustomList parameters = new CustomList();
	

	
	
	public int addUser(User user) throws SQLException {
		parameters.add("string", "user_name",user.getName());
		parameters.add("int", "age", user.getAge());
		parameters.add("string", "phone_number", user.getMobileNumber());
		parameters.add("string", "gender", user.getGender());
		parameters.add("string", "email", user.getEmailId());
		int res= DatabaseManager.addRecord(DatabaseManager.insertSQL(parameters, "User"));
		parameters.clear();
		return res;
	}

	public User getUserById(int id) throws SQLException , UserException {
		User user = null;
        String sql="select * from User where user_id="+id;
        ResultSet rs = DatabaseManager.getRecords(sql);
		boolean result=rs.next();
		if(result) {
			user = new  User(rs.getInt(1),
					rs.getString(2),
					rs.getInt(3),
					rs.getString(5),
					rs.getString(4),
					rs.getString(6));
		}
		if(new UserException().validate(user))
			return user;
		else
			throw new UserException("User Not Registered!");
		
	}
	
	public ArrayList<User> getAllUsers() throws SQLException{
		ArrayList<User> users = new ArrayList<User>();
		User user=null;
		String sql ="select * from User";

		ResultSet rs = DatabaseManager.getRecords(sql);
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

@SuppressWarnings("serial")
class UserException extends Exception{
	public UserException() {}
	UserException(String message){
		super(message);
	}
	
	boolean validate(User user) {
		
		if(user != null)
			return true;
		return false;
	}
	
}


