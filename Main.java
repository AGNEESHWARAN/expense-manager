package splitwise;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;






public class Main {

	
	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		final Scanner scn = new Scanner(System.in);
		Manager manager = new Manager();
		
		int ch=0;
		boolean status = true;
		HashMap<String,String> parameters= new HashMap<String, String>();
	
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
				//User user = new UserManager().userPrompt();
				parameters.put("name","String");
				parameters.put("age","int");
				parameters.put("gender","String");
				parameters.put("phone","String");
				parameters.put("email","String");
				User user = (User) prompt(parameters, new User(),null);
				manager.addUser(user.getName(), user.getAge(), user.getGender(), user.getMobileNumber(), user.getMobileNumber());
				System.out.println(" user added to database!!"+user.getName()+"  "+user.getAge());
				break;
			case 2:
				parameters.put("name","String");
				System.out.println("select users by ID:");
				System.out.println("\nID\tName\tGender\tNumber");
				ArrayList<Object> users = new ArrayList<Object>();
				for(User usr:manager.getAllUser()) {
					System.out.println(usr.getId()+"\t"+
							usr.getName()+"\t"+
							usr.getGender()+"\t"+
							usr.getMobileNumber());
				}
				while(true) {
					try {
						System.out.println("USER ID : (exit to stop)");
						String s = scn.next();
						if (s.equalsIgnoreCase("exit"))
							break;
						int i = Integer.parseInt(s);
						User usr=manager.getUser(i);
						users.add(usr);
					}catch (Exception e) {
						System.out.println("wrong format!");
					}
				}
					Group g = (Group) prompt(parameters, new Group(), users);
					System.out.println(g.getGroupName());
					for(User u : g.getUsers())
						System.out.println(u.getName());
					int gId=manager.createGroup(g.getGroupName());
					g.setId(gId);
					manager.addGroupMembers(g);
					System.out.println("done!");
				
				break;
			
			case 3:
				parameters.put("name","String");
				parameters.put("event_type_ group/individual","String");
				Event event = (Event) prompt(parameters, new Event(), null);
				int id;
				String str;
				if(event.getType().contains("g")) {
					manager.getAllGroup();
					while(true) {
						try {
							System.out.println("group id:");
							str = scn.next();
							id = Integer.parseInt(str);
							Group group = manager.getGroup(id);
							if(group!=null) {
								
								event = new GroupEvent(event.getName(),event.getType());
								((GroupEvent) event).setGroup(group);
								break;
							}
						} catch (Exception e) {
							System.out.println("Try again");
							
						}
					}
				}else if(event.getType().contains("i")) {
					for(User usr:manager.getAllUser()) {
						System.out.println(usr.getId()+"\t"+
								usr.getName()+"\t"+
								usr.getGender()+"\t"+
								usr.getMobileNumber());
					}
					while(true) {
						
						try {
							System.out.println("user id");
							str = scn.next();
							id = Integer.parseInt(str);
							User usr = manager.getUser(id);
							if(usr!=null) {
								event= new IndividualEvent(event.getName(), event.getType());
								((IndividualEvent)event).setUser(usr);
								break;
							}
						} catch (Exception e) {
							System.out.println("Try again");
						}
					}
				}
				
				
				if(event instanceof GroupEvent) {
					manager.addEvent(((GroupEvent)event));
					System.out.println(((GroupEvent)event).getGroup().getGroupName() +" added to database!");
				}
				else if(event instanceof IndividualEvent) {
					manager.addEvent(((IndividualEvent)event));
					System.out.println(((IndividualEvent)event).getUser().getName());
					}
				break;
				
			case 4:
				parameters.put("type_purchase/lend", "String");
				parameters.put("notes", "String");
				parameters.put("share_type_group/individual", "String");
				parameters.put("event_id", "int");
				manager.getAllEvent();
				Bill bill =null;
				try {
					while(true) {
						bill = (Bill) prompt(parameters, new Bill(), null);
						if(manager.getEvent(bill.getEventId()).getType().contains("i"))
							bill.setType("i");
						if((bill.getShareType().contains("g") || bill.getShareType().contains("i")) && (bill.getType().contains("p") || bill.getType().contains("l")))
							break;
						System.out.println("invalid shareill type it should  be either ('group' or 'g') or ('individual' or 'i')"
								+ "or invalid bill type it should be either ('purchase' or 'p') or ('lend' or 'l')");
					}
				} catch (Exception e2) {
					System.out.println("Try again! problem with event ID");
					break;
				}
				System.out.println("bill ---"+bill);
				if(bill !=null) {
					
					//add bill to bill BD
					
					parameters.clear();
					int billId = manager.addBill(bill);
					double total = bill.getTotal();
					if(bill.getType().equalsIgnoreCase("purchase") || bill.getType().equalsIgnoreCase("p") ) {
						System.out.println("Enter no.of items to be added :");
						int n = scn.nextInt();
						ArrayList<Purchase> purchases = new ArrayList<Purchase>();
						while(n>=1) {
							System.out.println("***********************");
							parameters.put("product_name", "String");
							parameters.put("type", "String");
							parameters.put("amount", "double");
							Purchase purchase = (Purchase) prompt(parameters, new Purchase(), null);
							purchase.setBillId(billId);
							total+= purchase.getAmount();
							purchases.add(purchase);
							manager.addPurchase(billId, purchase);
							
							// add purchase to purchase DB 
							
							
							n--;
							System.out.println("***********************");
						}
						manager.updateBill(billId, total);
						bill.setTotal(total);
						System.out.println(bill.getShareType());
						
						if(bill.getShareType().contains("g")) {
					
							GroupEvent evnt = (GroupEvent) manager.getEvent(bill.getEventId());
							Group group = ((GroupEvent)evnt).getGroup();
							
							ArrayList<Double> amt = new ArrayList<Double>();
							ArrayList<Double> perc = new ArrayList<Double>();
							System.out.println("1. SPLIT EQUAL\n"
									+ "2. SPLIT BY PERCENT :");
							int spl = 1;
							while(true) {
								try {
									String s = scn.next();
									spl = Integer.parseInt(s);
									if(spl==1 || spl==2)
										break;
								} catch (Exception e) {
									System.out.println("TRY AGAIN!");
								}
							}
							Integer[] usrs = new Integer[group.getUsers().size()];
							
							double shar=0;
							
							while(total!=0 || shar !=100) {
								int user_c=0;
								shar =0;
								total = bill.getTotal();
								amt.clear();
								perc.clear();
							System.out.println("need to pay "+total );
								for(User U: group.getUsers()) {
									
									System.out.println(U.getId()+"  "+U.getName());
								
								
									if(spl==1) {
										shar=100;
										while(true) {
											try {
												System.out.println(" payed amount : bal-"+total);
												String s = scn.next();
												if(s.equalsIgnoreCase("nil")) {
													break;
												}
												while(Double.parseDouble(s)>total) {
													System.out.println(" payed amount : bal-"+total);
													s = scn.next();
												}
												amt.add(Double.parseDouble(s));
												total-=Double.parseDouble(s);
												
												usrs[user_c]=U.getId();
												user_c++;
												break;
											} catch (Exception e) {
												System.out.println("Try again");
											}
										}
									}else if(spl ==2) {
										while(true) {
											try {
												System.out.println(" payed amount : bal -"+total +" " +(100-shar));
												String s = scn .next();
												if(s.equalsIgnoreCase("nil")) {
													break;
												}
												while(Double.parseDouble(s)>total) {
													System.out.println(" payed amount : bal-"+total +" "+(100-shar));
													s = scn.next();
												}
												amt.add(Double.parseDouble(s));
												total-=Double.parseDouble(s);
												System.out.println("Share owned : ");
												s = scn.next();
												perc.add(Double.parseDouble(s));
												shar+=Double.parseDouble(s);
												usrs[user_c]=U.getId();
												user_c++;
												
												break;
											}catch (Exception e) {
												System.out.println(e.getMessage()+"  TRY AGAIN:");
											}
										}
									}
								}
							}
							
							if(spl==1) {
								Double[] arr = new Double[amt.size()];
								arr =amt.toArray(arr);
								HashMap<Integer,HashMap<Integer,Double>> res = evnt.splitEqual(usrs,arr, bill.getTotal());
								System.out.println(res);
								Double share =  bill.getTotal()/arr.length;
								double r;
								System.out.println(share);
								for(int i=0;i<arr.length;i++) {
									if(arr[i]>share) {
										r=arr[i]-share;
											System.out.println(usrs[i]+"needs  to get"+ r);
											manager.addExpense(billId, usrs[i],arr[i],0.0, r);
										}
										else if(arr[i]<share) {
											r=share -arr[i];
											
											System.out.println(usrs[i]+" need to pay ="+r);
											manager.addExpense(billId, usrs[i], arr[i], r, 0.0);
										}
										else {
											manager.addExpense(billId, usrs[i], arr[i], 0.0, 0.0);
										}									
								}
								Iterator<Integer> l1_key = res.keySet().iterator();
									while(l1_key.hasNext()) {
										int lk_1=l1_key.next();
										
										HashMap<Integer,Double> res2 = res.get(lk_1);
										Iterator<Integer> l2_key = res2.keySet().iterator();
										while(l2_key.hasNext()) {
											int lk_2=l2_key.next();
											
											manager.addTOSplitRecord(lk_1, lk_2, res2.get(lk_2), billId);
											
										}
									}
									
								}						
												
							if(spl==2) {
								Double r;
								total = bill.getTotal();
								Double[] arr = new Double[amt.size()];
								arr =amt.toArray(arr);
								Double[] share = new Double[perc.size()];
								share = perc.toArray(share);
								HashMap<Integer, HashMap<Integer, Double>> res = evnt.splitByPercentage(usrs, arr, bill.getTotal(), share);
								System.out.println(res);
								Double per =  (total/100);
								for(int i=0;i<arr.length;i++) 
									System.out.println(usrs[i]+"  "+arr[i]);
								for(int i=0;i<share.length;i++) {
									Double amot = (per *share[i]);
									if(amot<arr[i]) {
										r=arr[i]-amot;
										//System.out.println(amt+"--"+arr[i]+"--"+(arr[i]-amt));
										System.out.println(usrs[i]+" needs to get---->"+r);
										manager.addExpense(billId, usrs[i],arr[i],0.0, r);
									}
									else if(amot>arr[i]) {
										r=amot-arr[i];
										//System.out.println(amt+"--"+arr[i]+"--"+(amt-arr[i]));
										System.out.println(usrs[i]+" needs to pay ---->"+ r);
										manager.addExpense(billId, usrs[i], arr[i], r, 0.0);
									}
									else {
										manager.addExpense(billId, usrs[i], arr[i], 0.0, 0.0);
									}
								}
								Iterator<Integer> l1_key = res.keySet().iterator();
								while(l1_key.hasNext()) {
									int lk_1=l1_key.next();
									
									HashMap<Integer,Double> res2 = res.get(lk_1);
									Iterator<Integer> l2_key = res2.keySet().iterator();
									while(l2_key.hasNext()) {
										int lk_2=l2_key.next();
										manager.addTOSplitRecord(lk_1, lk_2, res2.get(lk_2), billId);
										
									}
								}
								//add  records to Splitwise DB
							}
		
						}
						//individeal purchase
	
						else {
							Event evnt = manager.getEvent(bill.getEventId());
							User usr = ((IndividualEvent) evnt).getUser();
							System.out.println("USER IN THIS EVENT : "+usr.getId() +"  "+usr.getName());
							total = bill.getTotal();
							while(true) {
								try {
									System.out.println("user Id :");
									String s= scn.next();
									int uId = Integer.parseInt(s);
									User u = manager.getUser(uId);
									
									if(u != null && usr.getId()==uId) {
									
										manager.addExpense(billId, u.getId(), total, 0.0, 0.0);
										System.out.println("recept added successfully !!");
										break;
									}
								} catch (Exception e) {
									System.out.println("try again!"+ e.getMessage());
								}
		
							}
																			
						}
					
					}else if(bill.getType().equalsIgnoreCase("lend") || bill.getType().equalsIgnoreCase("l") ) {
						for(User u : manager.getAllUser()) {
							System.out.println(u.getId() +" ---"+ u.getName());
						}
						Event evnt = manager.getEvent(bill.getEventId());
						total = bill.getTotal();
						String s;
						int lid,rid,amt;
						if(evnt instanceof GroupEvent) {
							while(true) {
								try {
									System.out.println("enter lender person id:");
									s = scn.next();
									lid = Integer.parseInt(s);
									System.out.println("enter recipient person id:");
									s = scn.next();
									rid = Integer.parseInt(s);
									s = scn.next();
									amt = Integer.parseInt(s);
									manager.updateBill(bill.getId(), amt);
									if(((GroupEvent) evnt).isMember(lid) && ((GroupEvent) evnt).isMember(rid)) {
										manager.addExpense(billId, lid, amt, 0.0, amt);
										manager.addExpense(billId, rid, 0.0, amt, 0.0);
										manager.addTOSplitRecord(lid, rid, amt, billId);
										System.out.println("done!!"+amt);
										break;
									}
									System.out.println("member not in this group!");
								} catch (Exception e) {
									System.out.println("retry!");
								}
							}
						}else if(evnt instanceof IndividualEvent) {
							User luser=((IndividualEvent)evnt).getUser();
							for(User u:manager.getAllUser()) {
								if(luser.getId() != u.getId()) {
									System.out.println(u.getId() +" ---"+ u.getName());

								}
							}
							while(true) {
								try {										
									System.out.println("enter recipient person id:");
									s = scn.next();
									rid = Integer.parseInt(s);
									
									User ruser=manager.getUser(rid);
									System.out.println("Toal amount given :");
									s = scn.next();
									amt = Integer.parseInt(s);
									if(ruser!=null) {
										System.out.println(luser.getId() +" "+ruser.getId());
										manager.addExpense(billId, luser.getId(), amt, 0.0, amt);
										manager.addExpense(billId, rid, 0.0, amt, 0.0);
										manager.addTOSplitRecord(luser.getId() , rid, amt, billId);
										break;
									}
									}catch (Exception e) {
										System.out.println("try again");
									}
								}
							
						  }
					}
				}
				
				break;
			case 5:
				manager.getAllGroup();
				break;
			
			case 6:
				int usId;
				while(true) {
					try {
						System.out.println("enter user Id :");
						String s = scn.next();
						usId=Integer.parseInt(s);
						break;
					} catch (Exception e) {
						System.out.println("wrong format try again!");
					}
				}
				try {
					Iterator<Integer> it =manager.getAllExpenses(usId).keySet().iterator();
					while(it.hasNext()) {
						Event evnt = manager.getEvent(it.next());
						System.out.println("\n\t"+evnt.getName() +"   amount spent  "+new ExpenseManager().gettAllExpenses(usId).get(evnt.getId()) );
						
					}
				} catch (Exception e1) {}
				break;
			
			case 7:
				int uId;
				while(true) {
					try {
						System.out.println("enter user Id :");
						String s = scn.next();
						uId=Integer.parseInt(s);
						break;
					} catch (Exception e) {
						System.out.println("wrong format try again!");
					}
				}
				try {
					User usr;
					Iterator<Integer> ite = manager.getAllCredits(uId).keySet().iterator();
					while(ite.hasNext()) {
						usr = manager.getUser(ite.next());
						System.out.println("\n\t"+usr.getId()+"   "+usr.getName() +"   " +new  ExpenseManager().getAllCredits(uId).get(usr.getId())+"\n");
					}
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
				break;
			case 8:
				int usrId;
			while(true) {
				try {
					System.out.println("enter user Id :");
					String s = scn.next();
					usrId=Integer.parseInt(s);
					break;
				} catch (Exception e) {
					System.out.println("wrong format try again!");
				}
				
			}
				try {
					User u;
					System.out.println(usrId);
					Iterator<Integer> iter = manager.getAllDebts(usrId).keySet().iterator();
					while(iter.hasNext()) {
						u = manager.getUser(iter.next());
						System.out.println("\n\t"+u.getId()+"   "+u.getName() +"   " +new  ExpenseManager().getAllDebts(usrId).get(u.getId())+"\n");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
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
	
	
	@SuppressWarnings("unchecked")
	
	
	
	
	public static Object prompt(HashMap<String,String>parameters, Object returnObj,Object objectParameters) {
		HashMap<String,Integer> intDataType = new HashMap<String,Integer>();
		HashMap<String,String> stringDataType = new HashMap<String, String>();
		HashMap<String,Double> doubleDataType = new HashMap<String, Double>();
		
		Set<String> st = parameters.keySet();
		
		ArrayList<String> fields = new ArrayList<String>();
		
		for(String str: st)
			fields.add(str);
		
		ArrayList<String> dataTypes = new ArrayList<String>();
		
		for(int iter =0;iter<parameters.size();iter++) {
		dataTypes.add(parameters.get(fields.get(iter)));
	
		}
	
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);

		for(int i=0;i<fields.size();i++) {
			//System.out.println(fields.get(i));
			if(dataTypes.get(i).contains("int") || dataTypes.get(i).contains("integer")){
				while(true) {
					System.out.println(fields.get(i)+" :");
						try{
							String s = scan.nextLine();
							
							intDataType.put(fields.get(i),Integer.parseInt(s));
							break;
						
							}catch (Exception e) {
								System.out.println("please try again :");
						}
				}
			}
			else if(dataTypes.get(i).contains("String")){
				while(true) {
					System.out.println(fields.get(i)+" :");
					String s = scan.nextLine();
					
					stringDataType.put(fields.get(i),s);
					break;
					
				}
			}else if(dataTypes.get(i).contains("ouble") || dataTypes.get(i).contains("Double")){
				while(true) {
					System.out.println(fields.get(i)+" :");
						try{
							String s = scan.nextLine();
							doubleDataType.put(fields.get(i),Double.parseDouble(s));
							break;
						
							}catch (Exception e) {
								System.out.println("please try again :");
						}
				}
			}
		}
		try {
			if (returnObj instanceof User) {
				returnObj = new User(stringDataType.get("name"), intDataType.get("age"), stringDataType.get("gender"), stringDataType.get("phone"), stringDataType.get("email"));
			}else if(returnObj instanceof Event) {
				if(stringDataType.get("event_type_ group/individual").contains("p"))
					returnObj = new GroupEvent(stringDataType.get("name"),stringDataType.get("event_type_ group/individual"));
				else 
					returnObj =new IndividualEvent(stringDataType.get("name"),stringDataType.get("event_type_ group/individual"));
				
			}else if(returnObj instanceof Group) {
				
				returnObj= new Group(stringDataType.get("name"),(ArrayList<User>)objectParameters);
			}else if (returnObj instanceof Bill) {
				if(stringDataType.get("type_purchase/lend").contains("l") || stringDataType.get("type_purchase/lend").contains("end"))
					stringDataType.replace("share_type_group/individual","individual");
			
				returnObj = new Bill(stringDataType.get("type_purchase/lend"), stringDataType.get("share_type_group/individual"), stringDataType.get("notes"), intDataType.get("event_id"));
				
			}else if (returnObj instanceof Purchase) {
				returnObj = new Purchase(stringDataType.get("product_name"), stringDataType.get("type"), doubleDataType.get("amount"));
			}
			
			return returnObj;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
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



