package splitwise;

import java.sql.*;
import java.util.ArrayList;
public class EventManager extends AbstractEvent {
	
		DatabaseManager dm  = new DatabaseManager();
		String sql;
			
		public void addEvent(Event event) throws SQLException {
			sql="insert into Events_table(event_name,event_type) values('"+event.getName()+"','"+event.getType()+"')";
			int res= dm.addRecord(sql);
			if(event instanceof GroupEvent) 
				sql = "insert into Group_events(group_id,event_id) values('"+((GroupEvent) event).getGroup().getGroupId()+"',"
						+ "'"+res+"')";
			
			if(event instanceof IndividualEvent)
				
				sql = "insert into Individual_events(user_id,event_id) values('"+((IndividualEvent) event).getUser().getId()+"',"
						+ "'"+res+"')";
			
			dm.addRecord(sql);
			System.out.println(sql);
			System.out.println("Records Added !!");
		
		}
		
		public Event getEvent(String name) {
			Event event=null;
			
			try {
				sql="select * from Events_table where event_name='"+name+"'";
				ResultSet st = dm.getRecords(sql);
				st.next();
				int id =st.getInt(1);  
				
				String type = st.getString(3);
				if(type.equalsIgnoreCase("individual")) {
					ResultSet st1 = dm.getRecords("select * from Individual_events where event_id='"+id+"'");
					st1.next();
					User user = new UserManager().getUserById(st1.getInt(2));
					event = new IndividualEvent(id, name,user, type);
				}
				if(type.equalsIgnoreCase("group")) {
					ResultSet st1 = dm.getRecords("select * from Group_events where event_id='"+id+"'");
					st1.next();
					
					Group group = new GroupManager().getGroup(st1.getInt(2));
					
					event = new GroupEvent(id, name, group, type);
				}		
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			return event;
		}
		
		
		
		public Event getEvent(int id) {
			Event event=null;
			
			try {
				sql="select * from Events_table where event_id='"+id+"'";
				ResultSet st = dm.getRecords(sql);
				st.next();
				String name =st.getString(2);  
				
				String type = st.getString(3);
				if(type.equalsIgnoreCase("i") || type.contains("dividual")) {
					ResultSet st1 = dm.getRecords("select * from Individual_events where event_id='"+id+"'");
					st1.next();
					User user = new UserManager().getUserById(st1.getInt(2));
					event = new IndividualEvent(id, name,user, type);
				}
				if(type.equalsIgnoreCase("g") || type.contains("oup")) {
					ResultSet st1 = dm.getRecords("select * from Group_events where event_id='"+id+"'");
					st1.next();
					
					Group group = new GroupManager().getGroup(st1.getInt(2));
					
					event = new GroupEvent(id, name, group, type);
					
				}		
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			return event;
		}
		
		public ArrayList<Event> getAllEvent() throws SQLException{
			ArrayList<Event> events = new ArrayList<Event>();
			sql = "select * from Events_table";
			ResultSet st = dm.getRecords(sql);
			while(st.next()) {
				events.add(new Event(st.getInt(1), st.getString(2), st.getString(3)));
			}
			
			
			return events;
		}
	
}
