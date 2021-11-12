package splitwise;




import java.util.HashMap;

import java.util.Scanner;







public class Main {

	
	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		final Scanner scn = new Scanner(System.in);
		Manager manager = new Manager();
		
		int ch=0;
		boolean status = true;
		HashMap<String,String> parameters= new HashMap<String, String>();
		Driver driver = new Driver();
		try {
		while(status) {
			System.out.println("\n\n1. ADD USER \n"
					+ "2. CREATE NEW GROUP \n"
					+ "3. CREATE EVENT \n"
					+ "4. ADD RECEPT \n"
					+ "5. VIEW GROUP RECORDS \n"
					+ "6. VIEW EXPENSES \n"
					+ "7. CREDITS \n"
					+ "8. GET DEBTS \n"
					+ "ENTER YOUR CHOICE:\n");
			
			while(true) {
				try {
					String str = scn.next();
					ch = Integer.parseInt(str);
					break;
				} catch (Exception e) {
					System.out.println("Wrong Formatt!");
				}
			}
			
			parameters.clear();
			
			
			switch(ch) {
			case 1:
				driver.addUser();
				break;
				
			case 2:
				driver.addGroup();
				break;
			
			case 3:
				driver.addEvent();
				break;
				
			case 4:
				driver.addRecept();
				break;
			case 5:
				manager.getAllGroup();
				break;
			
			case 6:
				driver.viewExpenses();
				break;
			
			case 7:
				driver.viewCredits();
				break;
			case 8:
				driver.viewDebts();
				break;
				
			default:
				status = false;
				System.out.println("Exit!");
				break;
				}
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	
	
	 
}





















/* add group--
1. ask for group name
2. add group name in groups tabls
3. get the group id for last inserted dataObject
3. select users
4.create group
5. add groups to databas
			
			GroupManager gm = new GroupManager();
			gm.createGroup("Goa tour");
			User user1 = new UserManager().getUserById(2);
			User user2 = new UserManager().getUserById(3);
			ArrayList<User> users = new ArrayList<User>();
			users.add(user1);
			users.add(user2);
			Group g = new Group(3,"Goa tour",users);
			gm.addGroup(g);
			
*/

/*                                    
events
------- 
inserting into group events
  
 	GroupManager gm = new GroupManager();
 
	Group group = gm.getGroup(3);
	Event event = new GroupEvent("Goa trip", group, "group");
	EventManager em  = new EventManager();
	em.addEvent(event);

inserting Individual events

	User user = new UserManager().getUserById(1);
		IndividualEvent event = new IndividualEvent("Travel to Chennai", user, "individual");
		EventManager em = new EventManager();
		em.addEvent(event);
*/





//change group members entry_id to milli seconds



