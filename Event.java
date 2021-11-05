package splitwise;

public class Event  {
		
		
		private int id;

		private String name;
		
		private String type;

		
		public Event(int id,String name,String type) {
			this.setName(name);
			this.type = type;
			this.id = id;
			
		}
		
		public Event(String name,String type) {
			this(0,name,type);
		}
		public Event() {}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	

		public int getId() {
			return id;
		}


		public String getType() {
			return type;
		}

		
}
