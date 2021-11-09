package splitwise;

import java.sql.SQLException;


public abstract class AbstractEvent {

	abstract void addEvent(Event event) throws SQLException;
	
	abstract public Event getEvent(String name);
	
	abstract public Event getEvent(int id) throws EventException;
}
