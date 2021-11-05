package splitwise;

import java.sql.SQLException;
import java.util.ArrayList;

public class Manager {

	EventManager em = new EventManager();
	
	GroupManager gm = new GroupManager();
	
	ExpenseManager exm = new ExpenseManager();
	
	UserManager um = new UserManager();
	
	public Manager() {
		try {
			DatabaseManager.connectToDB();
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int addUser(String name, int age, String gender,String phoneNumber, String email) {
		User user = new User(name, age, gender, phoneNumber, email);
		int res =0;
		try {
		res=um.addUser(user);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	public User getUser(int id) {
		User user=null;
		try {
			user = um.getUserById(id);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return user;
	}
	
	public ArrayList<User> getAllUser(){
		try {
			return um.getAllUsers();
		} catch (Exception e) {
			System.out.println("No users Fount");
			return null;
		}
	}
	
	public int createGroup(String name) {
		int res =0;
		try {
			res = gm.createGroup(name);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
		
	public int addGroupMembers(Group group) {
		int res=0;
		try {
			res = gm.addGroup(group);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	public Group getGroup(int id) {
		Group group=null;
		try {
			group=gm.getGroup(id);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return group;
	}
	
	public int addBill(Bill bill) {
		int res = 0;
		try {
			res = exm.addBill(bill);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	public int addPurchase(int billId, Purchase purchase) {
		int res =0;
		try {
			res = exm.addPurchase(billId, purchase);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	public ArrayList<Bill> getBill(int eventId) {
		ArrayList<Bill>bills = new ArrayList<Bill>();
		try {
			bills = exm.getBill(eventId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return bills;
	}
	
	public void updateBill(int id, double total) {
		try {
			exm.updateBill(id, total);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Bill updated!");
	}
	
	public Event getEvent(int id) {
		Event event = null;
		try {
			event = em.getEvent(id);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("test");
		System.out.println(event);
		System.out.println("test2");
		return event;
		
	}
	
	public void addEvent(Event event) {
		try {
			em.addEvent(event);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public int addExpense(int billId, int userId, double amount, double debt, double credit) {
		int res =0;
		
		try {
			res = exm.addExpense(billId, userId, amount, debt, credit);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	public int addTOSplitRecord(int payee, int payer, double amount, int billId) {
		int res=0;
		try {
			res = exm.addTOSplitRecord(payee, payer, amount, billId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
}

