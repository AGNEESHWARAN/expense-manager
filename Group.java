package splitwise;

import java.util.ArrayList;

public class Group {
	
	private int id;
	
	private String groupName;
	
	private ArrayList<User> users;
	
	public Group(int id, ArrayList<User> users) {
		this(id,"",users);
	}
	public Group(int id,String groupName, ArrayList<User> users) {
		this.id =id;

		this.setGroupName(groupName);
		this.users =users;
	}
	
	public Group(String groupName, ArrayList<User> users) {
		this.id =0;

		this.setGroupName(groupName);
		this.users =users;
	}
	public Group() {}

	public ArrayList<User> getUsers() {
		return users;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public void editGroup(User user) {
		this.users.add(user);
	}
	public void editGroup(ArrayList<User> users) {
		this.users=users;
	}

	public int getGroupId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String[] getMembersName() {
		String[] res = new String[users.size()];
		
		for(int i=0;i<users.size();i++) {
			res[i]=users.get(i).getName();
		}
		
		return res;
	}
}
