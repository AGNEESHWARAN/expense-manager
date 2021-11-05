package splitwise;

public class User extends Person {
	

	private String emailId;
	
	public User(int id,String name,int age,String gender,String phoneNumber,String email) {
		super.setId(id);
		super.setName(name);
		super.setMobileNumber(phoneNumber);
		super.setAge(age);
		super.setGender(gender);
		this.emailId =email;
		
	}
	public User(String name,int age,String gender,String phoneNumber,String email) {
		
		this(0,name,age,gender,phoneNumber,email);
		
	}
	public User() {}
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
}
