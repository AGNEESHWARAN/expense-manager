package splitwise;
import java.sql.*;
import java.util.ArrayList;


public class GroupManager {
	private String sql;
	CustomList parameters = new CustomList();
	
	public int createGroup(String name) throws  ClassNotFoundException, SQLException {
		parameters.add("string", "group_name", name);
		int res =DatabaseManager.addRecord(DatabaseManager.insertSQL(parameters, "Groups_table") );
		parameters.clear();
		return res;
	}
	
	public void addMembers(int groupId,int memberId) throws SQLException {
		parameters.add("int", "group_id,", groupId);
		parameters.add("int", "user_id", memberId);
		DatabaseManager.addRecord(DatabaseManager.insertSQL(parameters, "Group_members"));
		parameters.clear();
		System.out.println("User Added !!");
	}
	
	public int addGroup(Group group) throws SQLException {
		int groupId = group.getGroupId();
		ArrayList<User> users = group.getUsers();
		for(User i : users) {
			parameters.add("int", "group_id", groupId);
			parameters.add("int", "user_id", i.getId());
			DatabaseManager.addRecord(DatabaseManager.insertSQL(parameters, "Group_members"));
			parameters.clear();
			}
		System.out.println("group Added!!");
		return groupId;
		}
	
	public Group getGroup(int id) throws SQLException, UserException, GroupException {
		Group group =null;
		ArrayList <User> users = new ArrayList<User>();
		sql="select * from Group_members where group_id='"+id+"'";
		ResultSet rs =DatabaseManager.getRecords(sql);
		while(rs.next()) {
	        User user = new UserManager().getUserById(rs.getInt(3)) ;	
	        users.add(user);
		}
		sql="select * from Groups_table where group_id='"+id+"'";
		ResultSet rs2 = DatabaseManager.getRecords(sql);
		if(rs2.next())
	       	group=new Group(id,rs2.getString(2),users);
		if(new GroupException().validate(group))
			return group;
		else
			throw new GroupException("NO group Found!");
	}
	public ArrayList<Group> getAllGroup() throws SQLException{
		ArrayList<Group> groups = new ArrayList<Group>();
		ArrayList<User> users = new ArrayList<User>();
		sql = "select * from Groups_table";
		ResultSet st = DatabaseManager.getRecords(sql);
		
		int uId;
		int gId;
		String groupName;
		while(st.next()) {
			gId = st.getInt(1);
			groupName = st.getString(2);
			
			sql = "select * from Group_members where group_id='"+gId+"'";
			ResultSet rs2 = DatabaseManager.getRecords(sql);
			while(rs2.next()) {
				uId= rs2.getInt(3);
				sql ="select * from User where user_id = '"+uId+"'";
				ResultSet st3 = DatabaseManager.getRecords(sql);
				st3.next();
				users.add(new User(uId,st3.getString(2), st3.getInt(3), st3.getString(5), st3.getString(4), st3.getString(6)));
					
			}
			
			groups.add(new Group(gId, groupName, new ArrayList<User>(users)));
			users.clear();
		}
	
	return groups;
	}
	
	
}

@SuppressWarnings("serial")
class GroupException extends Exception{
	GroupException(){}
	GroupException(String message){
		super(message);
	}
	
	boolean validate(Group group) {
		if(group != null)
			return true;
		return false;
	}
	
}