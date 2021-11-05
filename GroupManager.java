package splitwise;
import java.sql.*;
import java.util.ArrayList;


public class GroupManager {
	static final String DB_URL = "jdbc:mysql://localhost/splitwise";
	static final String USER = "guest";
	static final String PASS = "Guest@123+";
	private String sql;
	private DatabaseManager dm = new DatabaseManager();
	
	public int createGroup(String name) throws  ClassNotFoundException, SQLException {
		sql = "insert into Groups_table(group_name) values('"+name+"')";
		int res =dm.addRecord(sql);
		return res;
	}
	
	public void addMembers(int groupId,int memberId) throws SQLException {
		sql="insert into Group_members(group_id,user_id) values('"+groupId+"','"+memberId+"')";
		dm.addRecord(sql);
		System.out.println("User Added !!");
	}
	
	public int addGroup(Group group) throws SQLException {
		int groupId = group.getGroupId();
		ArrayList<User> users = group.getUsers();
		for(User i : users) {
			sql="insert into Group_members(group_id,user_id) values('"+groupId+"','"+i.getId()+"')";
			dm.addRecord(sql);
			}
		System.out.println("group Added!!");
		return groupId;
		}
	
	public Group getGroup(int id) throws SQLException {
		Group group =null;
		ArrayList <User> users = new ArrayList<User>();
		sql="select * from Group_members where group_id='"+id+"'";
		ResultSet rs =dm.getRecords(sql);
		while(rs.next()) {
	        User user = new UserManager().getUserById(rs.getInt(3)) ;	
	        users.add(user);
		}
		sql="select * from Groups_table where group_id='"+id+"'";
		ResultSet rs2 = dm.getRecords(sql);
		if(rs2.next())
	       	group=new Group(id,rs2.getString(2),users);
		return group;
	}
	
}
