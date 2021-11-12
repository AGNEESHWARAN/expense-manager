package splitwise;

import java.sql.*;
import java.util.ArrayList;
public class EventManager extends AbstractEvent {
		CustomList parameters = new CustomList();
		String sql;
			
		public void addEvent(Event event) throws SQLException {
			parameters.add("string", "event_name", event.getName());
			parameters.add("string", "event_type", event.getType());
			int res= DatabaseManager.addRecord(DatabaseManager.insertSQL(parameters, "Events_table"));
			parameters.clear();
			
			if(event instanceof GroupEvent) {
				
				parameters.add("int", "group_id", ((GroupEvent) event).getGroup().getGroupId());
				parameters.add("int", "event_id", res);
				DatabaseManager.addRecord(DatabaseManager.insertSQL(parameters, "Group_events"));
				
			}
			
			if(event instanceof IndividualEvent) {
				
				parameters.add("string", "user_id", ((IndividualEvent) event).getUser().getId());
				parameters.add("int", "event_id", res);
				DatabaseManager.addRecord(DatabaseManager.insertSQL(parameters, "Individual_events("));	
				
			}
			
			System.out.println("Records Added !!");
			parameters.clear();
		}
		
		public Event getEvent(String name) {
			Event event=null;
			
			try {
				sql="select * from Events_table where event_name='"+name+"'";
				ResultSet st = DatabaseManager.getRecords(sql);
				st.next();
				int id =st.getInt(1);  
				
				String type = st.getString(3);
				if(type.equalsIgnoreCase("individual")) {
					ResultSet st1 = DatabaseManager.getRecords("select * from Individual_events where event_id='"+id+"'");
					st1.next();
					User user = new UserManager().getUserById(st1.getInt(2));
					event = new IndividualEvent(id, name,user, type);
				}
				if(type.equalsIgnoreCase("group")) {
					ResultSet st1 = DatabaseManager.getRecords("select * from Group_events where event_id='"+id+"'");
					st1.next();
					
					Group group = new GroupManager().getGroup(st1.getInt(2));
					
					event = new GroupEvent(id, name, group, type);
				}		
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			return event;
		}
		
		
		
		public Event getEvent(int id) throws EventException {
			Event event=null;
			
			try {
				sql="select * from Events_table where event_id='"+id+"'";
				ResultSet st = DatabaseManager.getRecords(sql);
				st.next();
				String name =st.getString(2);  
				
				String type = st.getString(3);
				if(type.equalsIgnoreCase("i") || type.contains("dividual")) {
					ResultSet st1 = DatabaseManager.getRecords("select * from Individual_events where event_id='"+id+"'");
					st1.next();
					User user = new UserManager().getUserById(st1.getInt(2));
					event = new IndividualEvent(id, name,user, type);
				}
				if(type.equalsIgnoreCase("g") || type.contains("oup")) {
					ResultSet st1 = DatabaseManager.getRecords("select * from Group_events where event_id='"+id+"'");
					st1.next();
					
					Group group = new GroupManager().getGroup(st1.getInt(2));
					
					event = new GroupEvent(id, name, group, type);
					
				}		
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if(new EventException().validate(event))
				return event;
			else
				throw new EventException("Event not registered");
		}
		
		public ArrayList<Event> getAllEvent() throws SQLException{
			ArrayList<Event> events = new ArrayList<Event>();
			sql = "select * from Events_table";
			ResultSet st = DatabaseManager.getRecords(sql);
			while(st.next()) {
				events.add(new Event(st.getInt(1), st.getString(2), st.getString(3)));
			}
			
			
			return events;
		}
	
}

@SuppressWarnings("serial")
class EventException extends  Exception{
	EventException() {}
	
	EventException(String message) {
		super(message);
	}
	public boolean validate(Event event) {
		if(event !=null)
			return true;
		return false;
	}
}

