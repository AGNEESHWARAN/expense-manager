package splitwise;

public abstract class AbstractManager {

	abstract int addUser(String name, int age, String gender, String phoneNumber, String email);
	
	abstract int  createGroup(String name);
	
	abstract int addGroupMembers(Group group);
	
	abstract  int addBill(Bill bill);
	
	abstract int addPurchase(int billId, Purchase purchase);
	
	abstract void updateBill(int id, double total);

	abstract void addEvent(Event event);
	
	abstract int addExpense(int billId, int userId, double amount, double debt, double credit);
	
	abstract int addTOSplitRecord(int payee, int payer, double amount, int billId) ;
	
}
