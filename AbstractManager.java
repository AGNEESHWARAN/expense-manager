package splitwise;

public abstract class AbstractManager {

	abstract void createUser(String name, int age, String gender, String phoneNumber, String email);
	abstract void createGroup(String name, String type);
	abstract void addGroup(Group group);
}
