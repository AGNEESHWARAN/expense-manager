package splitwise;

public class IndividualEvent extends Event {
	
	private User user ;
	public IndividualEvent(int id, String name,User user,String type) {
		super(id, name,type);
		this.user=user;
	}
	public IndividualEvent(String name,String type) {
		super(name,type);
	}
	public IndividualEvent( String name,User user, String type) {
		super(0, name, type);
		this.user=user;
		
	}
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	

}
